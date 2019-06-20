package net.robomix.blackmoon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void sendNewLetter() {
        MimeMessagePreparator preparator = mimeMessage -> {
            mimeMessage.setFrom(new InternetAddress("mail@blackmoon.com.ua",
                    "Blackmoon Digital"));
            mimeMessage.setRecipient(Message.RecipientType.TO,
                    new InternetAddress("agahov@livegenic.com"));
            mimeMessage.setSubject("New project added to BlackMoon");
            mimeMessage.setText("Hello, Andrew. Mail works.");
        };
        mailSender.send(preparator);
    }
}
