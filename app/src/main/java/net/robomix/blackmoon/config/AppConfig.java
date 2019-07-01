package net.robomix.blackmoon.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.sentry.spring.SentryExceptionResolver;
import io.sentry.spring.SentryServletContextInitializer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.Properties;

@Configuration
@EnableAsync
public class AppConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(8);
    }

    @Bean
    public HandlerExceptionResolver sentryExceptionResolver() {
        return new SentryExceptionResolver();
    }

    @Bean
    public ServletContextInitializer sentryServletContextInitializer() {
        return new SentryServletContextInitializer();
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        // It supports the MimeMessage and SimpleMailMessage

        String sender = "mail@blackmoon.com.ua";
        String senderPass = "black1";
        String host = "smtp.yandex.ru";

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(465);
        mailSender.setUsername(sender);
        mailSender.setPassword(senderPass);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtps");
        props.put("mail.debug", "true");

        return mailSender;
    }

    @Bean
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }
}
