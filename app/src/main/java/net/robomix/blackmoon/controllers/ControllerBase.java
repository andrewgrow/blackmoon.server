package net.robomix.blackmoon.controllers;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@Component
@ControllerAdvice
public class ControllerBase {

    @ExceptionHandler(value = AccessDeniedException.class)

    public ModelAndView accessDenied() {
        return new ModelAndView("redirect:/login?error&error_message=Access denied");
    }
}
