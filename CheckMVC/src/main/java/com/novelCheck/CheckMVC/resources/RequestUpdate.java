package com.novelCheck.CheckMVC.resources;

import com.novelCheck.CheckMVC.models.KatalogNovel;
import com.novelCheck.CheckMVC.models.NovelChap;
import com.novelCheck.CheckMVC.models.UserKatalog;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

//@RestController
@Controller//do usunięcia
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
    /*

    @GetMapping("/all")
    public List<KatalogNovel> getKatalog(){
        UserKatalog userKatalog = restTemplate.getForObject("http://UPDATE-DBH2/db/getAll",UserKatalog.class);
        //UserKatalog userKatalog = restTemplate.getForObject("http://UPDATE-LIST/update/users/"+userID,UserKatalog.class);//w inf zwrotnej mam obiekt UserKatalog class
        return update(userKatalog);

    }*/

    @RequestMapping("/all")//zmienić na all
    public ModelAndView getKatalog(){

        UserKatalog userKatalog = restTemplate.getForObject("http://UPDATE-DBH2/db/getAll",UserKatalog.class);
        ModelAndView modelAndView = new ModelAndView("novels");
        modelAndView.addObject("katalog",update(userKatalog));

        return modelAndView;//do jakiego html idzie
    }


    @GetMapping("getNovel/{novelID}")
    public ModelAndView getUserKatalog(@PathVariable("novelID") String novelID) {
        //System.out.println("http://UPDATE-DBH2/db/getNovel/"+novelID);
        UserKatalog userKatalog = restTemplate.getForObject("http://UPDATE-DBH2/db/getNovel/"+novelID, UserKatalog.class);
        ModelAndView modelAndView = new ModelAndView("novelka");
        modelAndView.addObject("katalog",update(userKatalog));

        return modelAndView;//do jakiego html idzie
    }
    @GetMapping("getStrona/{strona}")
    public ModelAndView getStronaKatalog(@PathVariable("strona") String strona) {
        //System.out.println("http://UPDATE-DBH2/db/getNovel/"+novelID);
        UserKatalog userKatalog = restTemplate.getForObject("http://UPDATE-DBH2/db/getStrona/"+strona, UserKatalog.class);
        ModelAndView modelAndView = new ModelAndView("strona");
        modelAndView.addObject("katalog",update(userKatalog));

        return modelAndView;//do jakiego html idzie
    }
}
