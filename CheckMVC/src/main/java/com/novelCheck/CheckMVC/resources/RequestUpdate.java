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

    @GetMapping("/getUserNovel")
    public ModelAndView getUserNovel(){//@RequestParam(value = "username")String username
        String username = "asd";
        UserList userList = restTemplate.getForObject("http://UPDATE-DBH2/db/getUserNovel/"+username,UserList.class);//tutaj problem
        ModelAndView modelAndView = new ModelAndView("getUserNovel");

        List<UserUser> userUsers = userList.getUserUser().stream().map(novelka -> {
            return new UserUser(novelka.getUserName(),novelka.getEmail(),novelka.getSubNovel());
        }).collect(Collectors.toList());//pawie ale zwraca referecnje
        modelAndView.addObject("katalog",userUsers);
        //modelAndView.addObject("katalog2",userUsers);
        return modelAndView;//do jakiego html idzie
    }
}
