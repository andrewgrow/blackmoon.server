package net.robomix.blackmoon;

import io.sentry.Sentry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@PropertySources({
        @PropertySource(value = "classpath:application.properties"),
        @PropertySource(value = "classpath:common.properties")
})
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
//        Sentry.capture("Application Started");
        SpringApplication.run(Application.class, args);
    }
}
