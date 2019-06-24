package net.robomix.blackmoon.config;

import net.robomix.blackmoon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/", "/registration", "/static/**", "/login")
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
//        .and()
//            .exceptionHandling()
//                .accessDeniedHandler(new CustomAccessDeniedHandler())
        ;
    }

//    @Override
//    public void configure(WebSecurity web) throws Exception {
//            web.debug(true);
//    }

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
                    + "/login?error&error_message=" + exception.getMessage());
        }
    }

//    private static class CustomAccessDeniedHandler implements AccessDeniedHandler {
//        @Override
//        public void handle(HttpServletRequest request, HttpServletResponse response,
//                           AccessDeniedException exception) throws IOException {
//            response.setStatus(HttpStatus.FORBIDDEN.value());
//            response.sendRedirect(request.getContextPath()
//                    + "/?error_message=Access Denied (" + exception.getMessage() + ")");
//        }
//    }
}
