package net.robomix.blackmoon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    private MimeMessagePreparator envelop(List<String> recipientsList, String subject, String text) {
        return mimeMessage -> {
            Address[] recipients = recipientsList.stream().map(mail -> {
                try {
                    return new InternetAddress(mail);
                } catch (AddressException e) {
                    return null;
                }
            }).filter(Objects::nonNull).toArray(Address[]::new);

            mimeMessage.setFrom(new InternetAddress("mail@blackmoon.com.ua", "Blackmoon Digital"));
            mimeMessage.setRecipients(Message.RecipientType.TO, recipients);
            mimeMessage.setSubject(subject);
            mimeMessage.setText(text);
        };
    }

    @Async
    public void sendNewLetter() {
        List<String> recipients = new ArrayList<>();
        recipients.add("andrew.gahov@gmail.com");
        mailSender.send(envelop(recipients, "New project added to BlackMoon", "Hello, Andrew. Mail works."));
    }

    @Async
    public void notifyAdminsAboutUser(List<String> allAdminsEmail) {
        if (allAdminsEmail == null || allAdminsEmail.isEmpty()) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        System.out.println("all admins email : ");
        for (String s : allAdminsEmail) {
            System.out.println("--- : " + s);
            stringBuilder.append(s).append(", ");
        }
        String text = "Hello. New User was registered in BlackMoon.Server, so you have to set it as active. "
                + "\n"
                + "The email was sent to: " + stringBuilder.toString();
        mailSender.send(envelop(allAdminsEmail, "New User was registered in BlackMoon.Server", text));
    }
}
