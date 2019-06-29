package net.robomix.blackmoon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@PropertySources({
        @PropertySource(value = "classpath:application.properties"),
        @PropertySource(value = "classpath:common.properties")
})
@SpringBootApplication
public class App {
    public static void main(String[] args) {
//        Sentry.capture("Application Started");
        SpringApplication.run(App.class, args);
    }
}
