package com.novelCheck.novelMail.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.novelCheck.novelMail.model.*;
import com.sun.mail.imap.protocol.UIDSet;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;


@Controller
@EnableScheduling
public class NovelMailControler {

    private final NovelMail emailSender;
    private final TemplateEngine templateEngine;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    public NovelMailControler(NovelMail emailSender,
                              TemplateEngine templateEngine){
        this.emailSender = emailSender;
        this.templateEngine = templateEngine;
    }
    @RequestMapping("/")
    public String send() {
        Context context = new Context();//te poniżej są do thymeleaf do umiesczania
        //to będzie na wstępie
        context.setVariable("header", "asd");
        context.setVariable("title", "#tytttytyt");
        context.setVariable("description", "oppppp");
        //body


        String body = templateEngine.process("template", context);
        emailSender.sendMail("excellmeh@gmail.com", "novelChek", body);//tutaj błąd
        return "index";
    }
    @GetMapping(value = "/sendMailUser/{userName}")
    public String sendMailUser(@PathVariable("userName") String userName){

        UserList userList =restTemplate.getForObject("http://UPDATE-DBH2/db/getUserNovel/"+userName,UserList.class);//tutaj problem
        // mam zwrapowane w liste
        List<UserUser> users = userList.getUserUser().stream().map(novels ->
        {
            return new UserUser(novels.getUserName(),novels.getEmail(),novels.getSubNovel());
        }).collect(Collectors.toList());//getSubNOvel zwraca mi objety a nie

        UserUser user = (UserUser) users.get(0);//działa ale dalej list zwraca obiekty

        List<KatalogUpdate> kat2 = user.getSubNovel().stream().map(novels ->{
            return new KatalogUpdate(novels.getNovelID(),novels.getStrona());
        }).collect(Collectors.toList());

        List<KatalogNovel> katalogNovels = user.getSubNovel().stream().map(novelka -> {//problem w konstukcji url//Już działa
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

        Context context = new Context();//te poniżej są do thymeleaf do umiesczania
        context.setVariable("header", "asd");
        //context.setVariable("title", "#tytttytyt"+userKatalogWrapper.getUserUser().getUserName());
        context.setVariable("description", "oppppp"+userName);

        //System.out.println(userUser.getSubNovel().toString());//zwraca null czyli nie prekazuje listy

        System.out.println(userName);

        String body = templateEngine.process("template", context);//niechce poprawnie html słać
        LocalDateTime dateTime = LocalDateTime.now();//


        //emailSender.sendMail(userUser.getEmail(), "novelChek " + dateTime, body);//tutaj był body z temEngnie
        return "index";
    }

    ////		sec  min  h  dzien mies ?dzienTyg? rok//* wszystkie ? jakikolwiek
    @Scheduled(cron = "0 7 23 * * ?")//powinno o 20 codziennie wysyłać?
    @GetMapping("/L")
    public void periodSendMail(){

        UserList userList = restTemplate.getForObject("http://UPDATE-DBH2/db/mailGetAll",UserList.class);

        List<UserUser> userUsers = userList.getUserUser();

        Context context = new Context();//te poniżej są do thymeleaf do umiesczania
        //to będzie na wstępie
        context.setVariable("header", "asd");
        context.setVariable("title", "#tytttytyt");
        context.setVariable("description", "oppppp");

        String body = templateEngine.process("template", context);

        userUsers.forEach(userUser -> {
            emailSender.sendMail(userUser.getEmail(),"NovelCheck",body);
            List<KatalogUpdate> katalogUpdates = userUser.getSubNovel();
            katalogUpdates.stream().map( katalogUpdate -> {
                if (katalogUpdate.getStrona().equals("NovelUpdates") ){//getnovelID jest OK a getStrona jest null
                    NovelChap novelChap = restTemplate.getForObject( "http://NOVELUPD-SCRAPER/scrape/" +katalogUpdate.getNovelID(),NovelChap.class);
                    return new KatalogNovel(novelChap.getChapNum() , novelChap.getChapLink(),katalogUpdate.getNovelID());
                    //TODO tutaj do thymeleaf umieszczam
                }
                else if (katalogUpdate.getStrona().equals("ScribbleHub")){
                    NovelChap novelChap = restTemplate.getForObject( "http://SCRIBBLEHUB-SCRAPER/scrape/" +katalogUpdate.getNovelID(),NovelChap.class);
                    String[] part = katalogUpdate.getNovelID().split("/");
                    String tytul = part[1];
                    return new KatalogNovel(novelChap.getChapNum() , novelChap.getChapLink(),tytul);
                }
                else {
                    return new KatalogNovel("ERROR" , "NIEPOPRAWNA","STRONA");
                }
            });
            System.out.println(userUser.getEmail());//do testu
        });
        //System.out.println(userUser.toString());
    }
}
