package net.robomix.blackmoon.service;

import net.robomix.blackmoon.database.models.db.User;
import net.robomix.blackmoon.utils.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    private final JavaMailSender mailSender;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Value("${hostname}")
    private String hostname;

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
    public void notifyNewProject() {
        List<String> recipients = new ArrayList<>();
        recipients.add("andrew.gahov@gmail.com");
        mailSender.send(envelop(recipients, "New project added to BlackMoon", "Hello, Andrew. Mail works."));
    }

    @Async
    public void notifyAboutNewRegistration(List<String> recipients, String activationCode) {
        if (recipients == null || recipients.isEmpty() || TextUtils.isEmpty(activationCode)) {
            return;
        }
        StringBuilder emailsBuilder = new StringBuilder();
        for (String s : recipients) {
            emailsBuilder.append(s).append(", ");
        }
        String text = "Hello. New User was registered in BlackMoon.Server, so you have to set it as active. "
                + "\n"
                + "Click here " + hostname + "/activation/" + activationCode + " to activate new user</a> " +
                "or click here "+ hostname + "/user>" + "to see all users."
                + "\n"
                + "\n"
                + "The email was sent to: " + emailsBuilder.toString();
        mailSender.send(envelop(recipients, "New User was registered in BlackMoon.Server", text));
    }

    @Async
    public void notifyUserAboutActivation(User user) {
        if (user == null || TextUtils.isEmpty(user.getEmail())) {
            return;
        }
        List<String> email = new ArrayList<>();
        email.add(user.getEmail());
        String text = "Congratulations! Your account has been activated. You can login "
                + hostname + "/login" + " to BlackMoon.Server";
        mailSender.send(envelop(email, "Your account is active", text));
    }
}
