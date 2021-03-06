package com.novelCheck.CheckMVC.resources;

import com.novelCheck.CheckMVC.models.*;
import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

//@RestController//rest controller nie lub thymeleaf, sprawdzić rozwiązanie
@Controller
//@RequestMapping("/check")
public class RequestUpdate {

    @Autowired
    private RestTemplate restTemplate;

    private List<KatalogNovel> update(UserKatalog userKatalog){

        return userKatalog.getNovels().stream().map(novelka -> {//problem w konstukcji url//Już działa
            if (novelka.getStrona().equals("NovelUpdates") ){//getnovelID jest OK a getStrona jest null
                NovelChap novelChap = restTemplate.getForObject( "http://NOVELUPD-SCRAPER/scrape/" +novelka.getNovelID(),NovelChap.class);
                if (novelChap != null) {
                    return new KatalogNovel(novelChap.getChapNum() , novelChap.getChapLink(),novelka.getNovelID());
                }
                else {//TODO może zamienić na jakiś custom throws??
                    return new KatalogNovel("ERROR" , "BŁĄD","NOVELCHAP");
                }
            }
            else if (novelka.getStrona().equals("ScribbleHub")){
                NovelChap novelChap = restTemplate.getForObject( "http://SCRIBBLEHUB-SCRAPER/scrape/" +novelka.getNovelID(),NovelChap.class);
                String[] part = novelka.getNovelID().split("/");
                String tytul = part[1];
                if (novelChap != null) {
                    return new KatalogNovel(novelChap.getChapNum() , novelChap.getChapLink(),tytul);
                }
                else {//TODO może zamienić na jakiś custom throws??
                    return new KatalogNovel("ERROR" , "BŁĄD","NOVELCHAP");
                }
            }
            else {
                return new KatalogNovel("ERROR" , "NIEPOPRAWNA","STRONA");
            }
        }).collect(Collectors.toList());
    }
    @RequestMapping({"/", "/index"})
    public String index(Model model){
        return "index";//aby do index an poczoątek wchodziło
    }

    @RequestMapping("/all")//zmienić na all
    public ModelAndView getKatalog() {
        ModelAndView modelAndView = new ModelAndView("all");
        UserKatalog userkatalog = restTemplate.getForObject("http://UPDATE-DBH2/db/getAll",UserKatalog.class);
        try{
            UserKatalog userKatalog = getKatalogHelp(userkatalog);
            modelAndView.addObject("katalog",update(userKatalog));

        } catch (NullUserKatalog nullUserKatalog) {
            nullUserKatalog.printStackTrace();
        }

        return modelAndView;//do jakiego html idzie
    }
    private UserKatalog getKatalogHelp(UserKatalog userkatalog) throws NullUserKatalog {

        if (userkatalog == null) {
            throw new NullUserKatalog("UserKatalog jest null");
        }
        else {
            return userkatalog;
        }
    }


    @GetMapping("/novelka")
    public ModelAndView getUserKatalog(@RequestParam("novelID") String novelID) {
        //System.out.println("http://UPDATE-DBH2/db/getNovel/"+novelID);
        UserKatalog userkatalog = restTemplate.getForObject("http://UPDATE-DBH2/db/getNovel/"+novelID, UserKatalog.class);
        ModelAndView modelAndView = new ModelAndView("novelka");

        try{
            UserKatalog userKatalog = getKatalogHelp(userkatalog);
            modelAndView.addObject("katalog",update(userKatalog));

        } catch (NullUserKatalog nullUserKatalog) {
            nullUserKatalog.printStackTrace();
        }

        return modelAndView;//do jakiego html idzie
    }
    @GetMapping("/strona")
    public ModelAndView getStronaKatalog(@RequestParam(value = "strona") String strona) {
        //System.out.println("http://UPDATE-DBH2/db/getNovel/"+novelID);
        UserKatalog userkatalog = restTemplate.getForObject("http://UPDATE-DBH2/db/getStrona/"+strona, UserKatalog.class);
        ModelAndView modelAndView = new ModelAndView("strona");

        try{
            UserKatalog userKatalog = getKatalogHelp(userkatalog);
            modelAndView.addObject("katalog",update(userKatalog));

        } catch (NullUserKatalog nullUserKatalog) {
            nullUserKatalog.printStackTrace();
        }

        return modelAndView;//do jakiego html idzie
    }
    @GetMapping("/newNovel")
    public String postNovelForm(Model model){
        model.addAttribute("newNovelForm",new KatalogUpdate());//nowy form na podstaiwe Kat, update
        return "newNovelForm";
    }

    @PostMapping(value = "/newNovel",produces = "application/json")//saveNovel/{strona}/{novelID}
    public String postNovelResult(@ModelAttribute("newNovelForm") KatalogUpdate katalogUpdate){//throws JSONException
//        System.out.println(katalogUpdate.getNovelID());
//        System.out.println(katalogUpdate.getStrona());
        try{
            DbUpdate dbUpdate = new DbUpdate();
            String novelJsonResult = restTemplate.postForObject("http://UPDATE-DBH2/db/saveNovel"
                    ,dbUpdate.DBpostNovelResult(katalogUpdate),String.class);
            return "index";
        }catch (JSONException e){
            System.out.println("Json error");
            e.printStackTrace();
            return "index";
        }catch (Exception e){
            System.out.println("inny error");
            e.printStackTrace();
            return "index";
        }

    }
    ////////////////////////////////// do odzyskiwania user z db
    @GetMapping("/getUserNovel")//TODO trzeba całe przebudować
    public ModelAndView getUserNovel() throws NullUserList {//@RequestParam(value = "username")String username//TODO tutaj
        ModelAndView modelAndView = new ModelAndView("getUserNovel");
        String username = "asd";
        UserList userList =restTemplate.getForObject("http://UPDATE-DBH2/db/getUserNovel/"+username,UserList.class);//tutaj problem
        // mam zwrapowane w liste
        List<UserUser> users;//getSubNOvel zwraca mi objety a nie
        if (userList != null) {
            users = userList.getUserUser().stream().map(novels ->
                    new UserUser(novels.getUserName(),novels.getEmail(),novels.getSubNovel())).collect(Collectors.toList());

            UserUser user = users.get(0);//działa ale dalej list zwraca obiekty

            List<KatalogUpdate> kat2 = user.getSubNovel().stream().map(novels ->
                    new KatalogUpdate(novels.getNovelID(),novels.getStrona())).collect(Collectors.toList());

            modelAndView.addObject("katalog",users);
            modelAndView.addObject("katalog2",kat2);
        }    else {
            throw new NullUserList("UserKatalog jest null");
        }

        return modelAndView;//do jakiego html idzie
    }

    //////////////////////////////////////////////do wysyłania mail
    @GetMapping("/sendMailUser")
    public String sendMailUser(Model model){
        model.addAttribute("sendMailUser",new UserUser());//nowy form na podstaiwe useruser
        return "sendMailUser";
    }
    @PostMapping(value = "/sendMailUser",produces = "application/json")//saveNovel/{strona}/{novelID}
    public String sendMailUser()  {
        //chce zpostować UserUser                              //nw czy potrzebne jest Model model

        String username = "asd";//TODO zmienić z temp val

        System.out.println("http://NOVELMAIL/sendMailUser/"+username);
        String novelJsonResult = restTemplate.getForObject("http://NOVELMAIL/sendMailUser/"+username,String.class);//tutaj bugi
        return "index";//                                   bez dok adresu po / działa
    }

    // dodawanie user i novel
    @GetMapping("/addUser")
    public String postAddUserForm(Model model){
        model.addAttribute("newUserForm",new UserUser());//nowy form na podstaiwe useruser
        return "newUserForm";
    }
    @PostMapping("/addUser")//temp
    public String AddUserFormResult(@ModelAttribute("newUserForm") UserUser userUser) {
        try{
            DbUpdate dbUpdate = new DbUpdate();
            String novelJsonResult = restTemplate.postForObject("http://UPDATE-DBH2/db/addUser"
                    ,dbUpdate.DVAddUserFormResult(userUser),String.class);
            return "newUser";
        }catch (JSONException e){
            System.out.println("Json error");
            System.out.println(e);
            e.printStackTrace();
            return "index";
        }catch (Exception e){
            System.out.println("inny error");
            System.out.println(e);
            e.printStackTrace();
            return "index";
        }
    }

    //dodawanie novelki do user
    @GetMapping("/subNoveltoUser")
    public String postNoveltoUserForm(Model model){
        model.addAttribute("Wrapper",new SubUserToNovelWrapper());//sprawdzić czy da się mniej dodać
        //                  alternatywnie dodać weryfikacje przez e-mail????
        //model.addAttribute("NovelName",new KatalogUpdate());//jak wyzej
        return "newNoveltoUserForm";
    }
    @PostMapping("/subNoveltoUser")//temp
    public String NoveltoUserFormResult(@ModelAttribute("Wrapper") SubUserToNovelWrapper wrapper)  {

        try{
            DbUpdate dbUpdate = new DbUpdate();
            String novelJsonResult = restTemplate.postForObject("http://UPDATE-DBH2/db/subNoveltoUser"
                    ,dbUpdate.SubUserToNovelWrapper(wrapper),String.class);
            return "newUser";
        }catch (JSONException e){
            System.out.println("Json error");
            System.out.println(e);
            e.printStackTrace();
            return "index";
        }catch (Exception e){
            System.out.println("inny error");
            System.out.println(e);
            e.printStackTrace();
            return "index";
        }

    }
    @GetMapping("/updateUserMail")
    public String updateUserMail(Model model){
        model.addAttribute("User",new UserUser());

        return "updateUserMail";
    }
    @PostMapping("/updateUserMail")
    public String updateUserMailPost(@ModelAttribute("User") UserUser user){

        UserUser userUser = new UserUser(user.getUserName(),user.getEmail());//tbh nwm czemu to działa

        restTemplate.postForObject("http://UPDATE-DBH2/db/updateUserMail",userUser,UserUser.class);

        return "index";
    }

    @GetMapping("/delSubNovel")//TODO zrobić aby wyświetlało jakie są subskrybwane novelki
    public String delSubNovel(Model model){
        model.addAttribute("Wrapper",new SubUserToNovelWrapper());

        return "delSubNovel";
    }
    @PostMapping(value = "/delSubNovel")
    public String delSubNovelPost(@ModelAttribute("Wrapper") SubUserToNovelWrapper wrapper)  {

        try{
            DbUpdate dbUpdate = new DbUpdate();
            String novelJsonResult = restTemplate.postForObject("http://UPDATE-DBH2/db/delSubNovel"
                    ,dbUpdate.SubUserToNovelWrapper(wrapper),String.class);
            return "newUser";
        }catch (JSONException e){
            System.out.println("Json error");
            System.out.println(e);
            e.printStackTrace();
            return "index";
        }catch (Exception e){
            System.out.println("inny error");
            System.out.println(e);
            e.printStackTrace();
            return "index";
        }
    }

}
