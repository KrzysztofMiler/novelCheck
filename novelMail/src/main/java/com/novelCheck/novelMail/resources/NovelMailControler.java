package com.novelCheck.novelMail.resources;

import com.novelCheck.novelMail.model.KatalogUpdate;
import com.novelCheck.novelMail.model.UserUser;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
public class NovelMailControler {

    private final NovelMail emailSender;
    private final TemplateEngine templateEngine;

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
    @PostMapping(value = "/sendMailUser",consumes = "application/json",produces = "application/json")
    public String sendMailUser(@RequestBody UserUser userUser){

        ///ModelAndView modelAndView = new ModelAndView("template");
        Context context = new Context();//te poniżej są do thymeleaf do umiesczania
        context.setVariable("header", "asd");
        context.setVariable("title", "#tytttytyt"+userUser.getUserName());
        context.setVariable("description", "oppppp"+userUser.getUserName());


//        List<KatalogUpdate> kat2 = userUser.getSubNovel().stream().map(novels ->{//tutaj mam null pointer
//            return new KatalogUpdate(novels.getNovelID(),novels.getStrona());
//        }).collect(Collectors.toList());

        context.setVariable("userUser",userUser);
//        context.setVariable("katalog2",kat2);

//        modelAndView.addObject("userUser",userUser);//powinno wszystko do temp dodać
//        modelAndView.addObject("katalog2",kat2);
        String body = templateEngine.process("template", context);//niechce poprawnie html słać
        LocalDateTime dateTime = LocalDateTime.now();//


        emailSender.sendMail(userUser.getEmail(), "novelChek " + dateTime, body);//tutaj był body z temEngnie
        return "index";
    }
}
