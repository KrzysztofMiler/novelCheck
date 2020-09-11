package com.novelCheck.requestupdate.resources;

import com.novelCheck.requestupdate.models.KatalogNovel;
import com.novelCheck.requestupdate.models.KatalogUpdate;
import com.novelCheck.requestupdate.models.NovelChap;
import com.novelCheck.requestupdate.models.UserKatalog;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/request")
public class RequestUpdate {

    private RestTemplate restTemplate = new RestTemplate();
    @RequestMapping("/{userID}")
    public List<KatalogNovel> getKatalog(@PathVariable("userID") String userID){

        UserKatalog userKatalog = restTemplate.getForObject("http://localhost:8082/update/users/"+userID,UserKatalog.class);//w inf zwrotnej mam obiekt UserKatalog class
       KatalogUpdate as = new KatalogUpdate();


        return userKatalog.getNovels().stream().map(novelka -> {//problem w konstukcji url//Już działa
            NovelChap novelChap = restTemplate.getForObject( "http://localhost:8080/scrape/" +novelka.getNovelID(),NovelChap.class);
            return new KatalogNovel( novelChap.getChapNum() , novelChap.getChapLink());//novelka.getNovelID()
        }).collect(Collectors.toList());

    }
}
