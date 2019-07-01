package net.robomix.blackmoon.config;

import net.robomix.blackmoon.service.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static net.robomix.blackmoon.Constants.ERROR_MESSAGE;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    public WebSecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/", "/registration", "/static/**", "/login", "/api/**")
                .permitAll()
            .anyRequest()
                .authenticated()
        .and()
            .formLogin()
            .loginPage("/login")
            .failureHandler(new CustomAuthenticationFailureHandler())
            .defaultSuccessUrl("/projects")
            .permitAll()
        .and()
            .logout()
            .logoutSuccessUrl("/")
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID")
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .permitAll()
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(userService.getPasswordEncoder());
    }

    private static class CustomAuthenticationFailureHandler
            implements AuthenticationFailureHandler {
        @Override
        public void onAuthenticationFailure(
                HttpServletRequest request,
                HttpServletResponse response,
                AuthenticationException exception) throws IOException {

            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.sendRedirect(request.getContextPath()
                    + "/login?error&" + ERROR_MESSAGE +"=" + exception.getMessage());
        }
    }
}
