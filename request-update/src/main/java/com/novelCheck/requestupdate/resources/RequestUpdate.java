package com.novelCheck.requestupdate.resources;

import com.novelCheck.requestupdate.models.KatalogNovel;
import com.novelCheck.requestupdate.models.KatalogUpdate;
import com.novelCheck.requestupdate.models.NovelChap;
import com.novelCheck.requestupdate.models.UserKatalog;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/request")
public class RequestUpdate {

    private RestTemplate restTemplate = new RestTemplate();
    @RequestMapping("/{userID}")
    /*public String shid(@PathVariable("userID") String userID){
        return "rip "+ userID;
    }*/
    public List<KatalogNovel> getKatalog(@PathVariable("userID") String userID){
        //return (List<KatalogNovel>) new KatalogNovel("2","asds");
        UserKatalog userKatalog = restTemplate.getForObject("http://localhost:8082/update/users/a",UserKatalog.class);//w inf zwrotnej mam obiekt UserKatalog class
       KatalogUpdate as = new KatalogUpdate();

        /*
        List<KatalogUpdate> katalog = Arrays.asList(
                new KatalogUpdate("the-second-coming-of-gluttony"),
                new KatalogUpdate("second-life-ranker")
        );*/
        //katalog
        String url ;//"second-life-ranker";
        return userKatalog.getNovels().stream().map(novelka -> {//problem w konstukcji url//Już działa
            NovelChap novelChap = restTemplate.getForObject( "http://localhost:8080/scrape/" +novelka.getNovelID(),NovelChap.class);
            return new KatalogNovel( novelChap.getChapNum() , novelChap.getChapLink());//novelka.getNovelID()
        }).collect(Collectors.toList());

    }
}
