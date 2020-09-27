package com.novelCheck.novelMail.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;



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
        Context context = new Context();
        context.setVariable("header", "Nowy artykuł na CodeCouple");
        context.setVariable("title", "#8 Spring Boot – email - szablon i wysyłanie");
        context.setVariable("description", "Tutaj jakis opis...");
        String body = templateEngine.process("template", context);
        emailSender.sendMail("excellmeh@gmail.com", "CodeCouple Newsletter", body);//tutaj błąd
        return "index";
    }

}
