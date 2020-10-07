package com.novelCheck.CheckMVC.resources;

import com.novelCheck.CheckMVC.models.*;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<KatalogNovel> update(UserKatalog userKatalog){

        return userKatalog.getNovels().stream().map(novelka -> {//problem w konstukcji url//Już działa
            if (novelka.getStrona().equals("NovelUpdates") ){//getnovelID jest OK a getStrona jest null
                NovelChap novelChap = restTemplate.getForObject( "http://NOVELUPD-SCRAPER/scrape/" +novelka.getNovelID(),NovelChap.class);
                return new KatalogNovel(novelChap.getChapNum() , novelChap.getChapLink(),novelka.getNovelID());
            }
            else if (novelka.getStrona().equals("ScribbleHub")){
                NovelChap novelChap = restTemplate.getForObject( "http://SCRIBBLEHUB-SCRAPER/scrape/" +novelka.getNovelID(),NovelChap.class);
                String[] part = novelka.getNovelID().split("/");
                String tytul = part[1];
                return new KatalogNovel(novelChap.getChapNum() , novelChap.getChapLink(),tytul);
            }
            else {
                return new KatalogNovel("ERROR" , "NIEPOPRAWNA","STRONA");
            }
        }).collect(Collectors.toList());
    }

    @RequestMapping("/index")
    public String index(Model model){
        return "index";//aby do index an poczoątek wchodziło
    }

    @RequestMapping("/all")//zmienić na all
    public ModelAndView getKatalog(){

        UserKatalog userKatalog = restTemplate.getForObject("http://UPDATE-DBH2/db/getAll",UserKatalog.class);
        ModelAndView modelAndView = new ModelAndView("all");
        modelAndView.addObject("katalog",update(userKatalog));

        return modelAndView;//do jakiego html idzie
    }


    @GetMapping("/novelka")
    public ModelAndView getUserKatalog(@RequestParam("novelID") String novelID) {
        //System.out.println("http://UPDATE-DBH2/db/getNovel/"+novelID);
        UserKatalog userKatalog = restTemplate.getForObject("http://UPDATE-DBH2/db/getNovel/"+novelID, UserKatalog.class);
        ModelAndView modelAndView = new ModelAndView("novelka");
        modelAndView.addObject("katalog",update(userKatalog));

        return modelAndView;//do jakiego html idzie
    }
    @GetMapping("/strona")
    public ModelAndView getStronaKatalog(@RequestParam(value = "strona") String strona) {
        //System.out.println("http://UPDATE-DBH2/db/getNovel/"+novelID);
        UserKatalog userKatalog = restTemplate.getForObject("http://UPDATE-DBH2/db/getStrona/"+strona, UserKatalog.class);
        ModelAndView modelAndView = new ModelAndView("strona");
        modelAndView.addObject("katalog",update(userKatalog));

        return modelAndView;//do jakiego html idzie
    }
    @GetMapping("/newNovel")
    public String postNovelForm(Model model){
        model.addAttribute("newNovelForm",new KatalogUpdate());//nowy form na podstaiwe Kat, update
        return "newNovelForm";
    }

    @PostMapping("/newNovel")//saveNovel/{strona}/{novelID}
    public String postNovelResult(@ModelAttribute("newNovelForm") KatalogUpdate katalogUpdate) throws JSONException {
        //chce zpostować KatalogUpdate                              //nw czy potrzebne jest Model model
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject novelJsonObject = new JSONObject();
        novelJsonObject.put("novelID",katalogUpdate.getNovelID());//to jest katalogUpdate
        novelJsonObject.put("strona",katalogUpdate.getStrona());

        HttpEntity<String> req = new HttpEntity<String>(novelJsonObject.toString(),headers);
        String novelJsonResult = restTemplate.postForObject("http://UPDATE-DBH2/db/saveNovel",req,String.class);
        return "newNovel";
    }
    ////////////////////////////////// do odzyskiwania user z db
    @GetMapping("/getUserNovel")
    public ModelAndView getUserNovel(){//@RequestParam(value = "username")String username
        String username = "asd";
        UserList userList =restTemplate.getForObject("http://UPDATE-DBH2/db/getUserNovel/"+username,UserList.class);//tutaj problem
        // mam zwrapowane w liste
        List<UserUser> users = userList.getUserUser().stream().map(novels ->
        {
            return new UserUser(novels.getUserName(),novels.getEmail(),novels.getSubNovel());
        }).collect(Collectors.toList());//getSubNOvel zwraca mi objety a nie

        UserUser user = (UserUser) users.get(0);//działa ale dalej list zwraca obiekty

        List<KatalogUpdate> kat2 = user.getSubNovel().stream().map(novels ->{
            return new KatalogUpdate(novels.getNovelID(),novels.getStrona());
        }).collect(Collectors.toList());


        ModelAndView modelAndView = new ModelAndView("getUserNovel");

        modelAndView.addObject("katalog",users);
        modelAndView.addObject("katalog2",kat2);

        return modelAndView;//do jakiego html idzie
    }
    //////////////////////////////////////////////do wysyłania mail
    @GetMapping("/sendMailUser")
    public String sendMailUser(Model model){
        model.addAttribute("sendMailUser",new UserUser());//nowy form na podstaiwe useruser
        return "sendMailUser";
    }
    @PostMapping("/sendMailUser")//saveNovel/{strona}/{novelID}
    public String sendMailUser() throws JSONException {//pewnie trzeba będie param pozmieniać
        //chce zpostować UserUser                              //nw czy potrzebne jest Model model

        String username = "asd";//TODO zmienić z temp val
//        UserList userList =restTemplate.getForObject("http://UPDATE-DBH2/db/getUserNovel/"+username,UserList.class);//tutaj problem
//        // mam zwrapowane w liste
//        List<UserUser> users = userList.getUserUser().stream().map(novels ->
//        {
//            return new UserUser(novels.getUserName(),novels.getEmail(),novels.getSubNovel());
//        }).collect(Collectors.toList());//getSubNOvel zwraca mi objety a nie
//
//        UserUser user = (UserUser) users.get(0);//działa ale dalej list zwraca obiekty
//
////        List<KatalogUpdate> kat2 = user.getSubNovel().stream().map(novels ->{
////            return new KatalogUpdate(novels.getNovelID(),novels.getStrona());
////        }).collect(Collectors.toList());
////
//        JSONObject userJsonObject = new JSONObject();
//        userJsonObject.put("userName",user.getUserName());
//        userJsonObject.put("email",user.getEmail());
//        HttpHeaders headers = new HttpHeaders();
//        //headers.setAccept(acceptableMediaTypes);
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//
//
//        HttpEntity<String> entity = new HttpEntity<String>(userJsonObject.toString(), headers);//TODO dodać dok url//normalnie był tu string.class
//        //bez string nie działa
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
    public String AddUserFormResult(@ModelAttribute("newUserForm") UserUser userUser) throws JSONException {
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject novelJsonObject = new JSONObject();
        novelJsonObject.put("userName",userUser.getUserName());//to jest katalogUpdate
        novelJsonObject.put("email",userUser.getEmail());

        HttpEntity<String> req = new HttpEntity<String>(novelJsonObject.toString(),headers);
        String novelJsonResult = restTemplate.postForObject("http://UPDATE-DBH2/db/addUser",req,String.class);
        return "newUser";
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
    public String NoveltoUserFormResult(@ModelAttribute("Wrapper") SubUserToNovelWrapper wrapper) throws JSONException {
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject novelJsonObject = new JSONObject();
        novelJsonObject.put("userName",wrapper.getUserName());//to jest katalogUpdate
        novelJsonObject.put("novelID",wrapper.getNovelID());

        HttpEntity<String> req = new HttpEntity<String>(novelJsonObject.toString(),headers);
        String novelJsonResult = restTemplate.postForObject("http://UPDATE-DBH2/db/subNoveltoUser",req,String.class);


        return "index";//z jakiegoś powodu nie widzi newNoveltoUser
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
    public String delSubNovelPost(@ModelAttribute("Wrapper") SubUserToNovelWrapper wrapper) throws JSONException {
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject novelJsonObject = new JSONObject();
        novelJsonObject.put("userName",wrapper.getUserName());//to jest katalogUpdate
        novelJsonObject.put("novelID",wrapper.getNovelID());

        HttpEntity<String> req = new HttpEntity<String>(novelJsonObject.toString(),headers);
        String novelJsonResult = restTemplate.postForObject("http://UPDATE-DBH2/db/delSubNovel",req,String.class);


        return "index";//z jakiegoś powodu nie widzi newNoveltoUser
    }

}
