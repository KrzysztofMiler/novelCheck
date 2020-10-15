package com.novelCheck.CheckMVC.resources;

import com.novelCheck.CheckMVC.models.KatalogUpdate;
import com.novelCheck.CheckMVC.models.SubUserToNovelWrapper;
import com.novelCheck.CheckMVC.models.UserUser;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@RestController
public class DbUpdate {
    @Autowired
    private RestTemplate restTemplate;

    public HttpEntity<String> DBpostNovelResult(KatalogUpdate katalogUpdate) throws JSONException {
        //chce zpostować KatalogUpdate                              //nw czy potrzebne jest Model model
        System.out.println(katalogUpdate.getNovelID());
        System.out.println(katalogUpdate.getStrona());

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        JSONObject novelJsonObject = new JSONObject();
        novelJsonObject.put("novelID",katalogUpdate.getNovelID());//to jest katalogUpdate
        novelJsonObject.put("strona",katalogUpdate.getStrona());

        HttpEntity<String> req = new HttpEntity<String>(novelJsonObject.toString(),headers);
        //String novelJsonResult = restTemplate.postForObject("http://UPDATE-DBH2/db/saveNovel",req,String.class);
        return req;
    }

    public HttpEntity<String> DVAddUserFormResult(UserUser userUser) throws JSONException {
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject novelJsonObject = new JSONObject();
        novelJsonObject.put("userName",userUser.getUserName());//to jest katalogUpdate
        novelJsonObject.put("email",userUser.getEmail());

        HttpEntity<String> req = new HttpEntity<String>(novelJsonObject.toString(),headers);
        return req;
    }

    public HttpEntity<String> SubUserToNovelWrapper( SubUserToNovelWrapper wrapper) throws JSONException {
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject novelJsonObject = new JSONObject();
        novelJsonObject.put("userName", wrapper.getUserName());//to jest katalogUpdate
        novelJsonObject.put("novelID", wrapper.getNovelID());

        HttpEntity<String> req = new HttpEntity<String>(novelJsonObject.toString(), headers);
        return req;
    }

//    public String delSubNovelPost( SubUserToNovelWrapper wrapper) throws JSONException {
//        HttpHeaders headers = new HttpHeaders();
//
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        JSONObject novelJsonObject = new JSONObject();
//        novelJsonObject.put("userName",wrapper.getUserName());//to jest katalogUpdate
//        novelJsonObject.put("novelID",wrapper.getNovelID());
//
//        HttpEntity<String> req = new HttpEntity<String>(novelJsonObject.toString(),headers);
//        String novelJsonResult = restTemplate.postForObject("http://UPDATE-DBH2/db/delSubNovel",req,String.class);
//
//
//        return "index";//z jakiegoś powodu nie widzi newNoveltoUser
//    }
}
