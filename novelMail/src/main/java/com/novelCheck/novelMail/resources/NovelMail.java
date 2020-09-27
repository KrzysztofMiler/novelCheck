package com.novelCheck.novelMail.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class NovelMail {

    @Autowired
    private JavaMailSender emailSender;

    public void sendMail (String to, String subject, String text){

        MimeMessage mail = emailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail,true);

            helper.setTo(to);
            helper.setReplyTo("noreply@novelchek.org");
            helper.setFrom("novelCheck");
            helper.setSubject(subject);
            helper.setText(text);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        emailSender.send(mail);//tutaj błą

    }
}
