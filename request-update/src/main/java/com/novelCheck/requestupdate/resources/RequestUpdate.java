package com.novelCheck.requestupdate.resources;

import com.novelCheck.requestupdate.models.KatalogNovel;
import com.novelCheck.requestupdate.models.KatalogUpdate;
import com.novelCheck.requestupdate.models.NovelChap;
import com.novelCheck.requestupdate.models.UserKatalog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/request")
public class RequestUpdate {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/{userID}")
    public List<KatalogNovel> getKatalog(@PathVariable("userID") String userID){

        UserKatalog userKatalog = restTemplate.getForObject("http://UPDATE-LIST/update/users/"+userID,UserKatalog.class);//w inf zwrotnej mam obiekt UserKatalog class
        System.out.println(userKatalog);
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
}
