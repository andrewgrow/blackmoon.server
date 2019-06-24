package net.robomix.blackmoon.controllers;

import net.robomix.blackmoon.database.models.dto.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@Component
@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(value = AccessDeniedException.class)
    public ModelAndView accessDenied(AccessDeniedException ex, @AuthenticationPrincipal UserDTO userDTO) {
        ModelAndView view = new ModelAndView(userDTO == null ? "redirect:/" : "redirect:/projects");
        view.addObject("error_message", "403 Access Denied");
        view.setStatus(HttpStatus.FORBIDDEN);
        return view;
    }
}
