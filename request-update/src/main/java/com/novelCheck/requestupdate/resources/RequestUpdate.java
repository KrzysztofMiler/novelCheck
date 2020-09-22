package com.novelCheck.requestupdate.resources;

import com.novelCheck.requestupdate.models.KatalogNovel;
import com.novelCheck.requestupdate.models.KatalogUpdate;
import com.novelCheck.requestupdate.models.NovelChap;
import com.novelCheck.requestupdate.models.UserKatalog;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/request")
public class RequestUpdate {

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

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/all")
    public List<KatalogNovel> getKatalog(){
        UserKatalog userKatalog = restTemplate.getForObject("http://UPDATE-DBH2/db/getAll",UserKatalog.class);
        //UserKatalog userKatalog = restTemplate.getForObject("http://UPDATE-LIST/update/users/"+userID,UserKatalog.class);//w inf zwrotnej mam obiekt UserKatalog class
        return update(userKatalog);

    }
    @GetMapping("getNovel/{novelID}")
    public List<KatalogNovel> getUserKatalog(@PathVariable("novelID") String novelID) {
        //System.out.println("http://UPDATE-DBH2/db/getNovel/"+novelID);
        UserKatalog userKatalog = restTemplate.getForObject("http://UPDATE-DBH2/db/getNovel/"+novelID, UserKatalog.class);
        return update(userKatalog);
    }
    @GetMapping("getStrona/{strona}")
    public List<KatalogNovel> getStronaKatalog(@PathVariable("strona") String strona) {
        //System.out.println("http://UPDATE-DBH2/db/getNovel/"+novelID);
        UserKatalog userKatalog = restTemplate.getForObject("http://UPDATE-DBH2/db/getStrona/"+strona, UserKatalog.class);
        System.out.println(userKatalog.getNovels().toString());//puste
        return update(userKatalog);//nwyraźniej nie dostaje info
    }
    @PostMapping("/saveNovel/{strona}/{novelID}")//
    public void postNovel(@PathVariable("strona") String strona,@PathVariable("novelID") String novelID) throws JSONException {
        //chce zpostować KatalogUpdate
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject novelJsonObject = new JSONObject();
        novelJsonObject.put("novelID",novelID);
        novelJsonObject.put("strona",strona);

        HttpEntity<String> req = new HttpEntity<String>(novelJsonObject.toString(),headers);
        String novelJsonResult = restTemplate.postForObject("http://UPDATE-DBH2/db/saveNovel",req,String.class);

    }
}
