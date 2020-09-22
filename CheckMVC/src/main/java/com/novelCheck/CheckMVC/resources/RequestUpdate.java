package com.novelCheck.CheckMVC.resources;

import com.novelCheck.CheckMVC.models.KatalogNovel;
import com.novelCheck.CheckMVC.models.KatalogUpdate;
import com.novelCheck.CheckMVC.models.NovelChap;
import com.novelCheck.CheckMVC.models.UserKatalog;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

//@RestController
@Controller//do usunięcia
//@RequestMapping("/check")
public class RequestUpdate {

    @Autowired
    private RestTemplate restTemplate;

    /*@GetMapping("/widgets")
    public String getTest(Model model){
        KatalogNovel katalogNovel = new KatalogNovel("asd","dsds","sff");
        model.addAttribute("widgets",katalogNovel.getChapLink());
        return "widgets";
    }*/

    @RequestMapping("/all")//zmienić na all
    public String getKatalog(Model model){
        /*
        //UserKatalog userKatalog = restTemplate.getForObject("http://UPDATE-LIST/update/users/"+userID,UserKatalog.class);//w inf zwrotnej mam obiekt UserKatalog class
        UserKatalog userKatalog = restTemplate.getForObject("http://UPDATE-DBH2/db/getAll",UserKatalog.class);
        //System.out.println(userKatalog);
         userKatalog.getNovels().stream().map(novelka -> {//problem w konstukcji url//Już działa
            if (novelka.getStrona().equals("NovelUpdates") ){//getnovelID jest OK a getStrona jest null
                NovelChap novelChap = restTemplate.getForObject( "http://NOVELUPD-SCRAPER/scrape/" +novelka.getNovelID(),NovelChap.class);
                System.out.println("asdsdsada");
                return new KatalogNovel(novelChap.getChapNum() , novelChap.getChapLink(),novelka.getNovelID());
            }
            else if (novelka.getStrona().equals("ScribbleHub")){
                NovelChap novelChap = restTemplate.getForObject( "http://SCRIBBLEHUB-SCRAPER/scrape/" +novelka.getNovelID(),NovelChap.class);
                String[] part = novelka.getNovelID().split("/");
                String tytul = part[1];
                System.out.println("asdsdsada2222222");
                return new KatalogNovel(novelChap.getChapNum() , novelChap.getChapLink(),tytul);
            }
            else {
                return new KatalogNovel("ERROR" , "NIEPOPRAWNA","STRONA");
            }
        }).collect(Collectors.toList());*/
        List<KatalogUpdate> katalog = Arrays.asList(
                new KatalogUpdate("the-second-coming-of-gluttony","NovelUpdates"),
                new KatalogUpdate("second-life-ranker","NovelUpdates"),
                new KatalogUpdate("overgeared","NovelUpdates"),
                new KatalogUpdate("41306/he-who-fights-with-monsters", "ScribbleHub")
        );


         model.addAttribute("novels",katalog);//problem z userKatal
        return "novel";
    }

}
