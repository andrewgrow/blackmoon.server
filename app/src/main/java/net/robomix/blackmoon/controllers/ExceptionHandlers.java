package net.robomix.blackmoon.controllers;

import net.robomix.blackmoon.database.models.dto.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(value = AccessDeniedException.class)
    public ModelAndView accessDenied(AccessDeniedException ex, @AuthenticationPrincipal UserDTO userDTO,
            HttpServletRequest request, HttpServletResponse response) {
        ModelAndView view = new ModelAndView(userDTO == null ? "redirect:/" : "redirect:/projects");
        view.addObject("error_message", "403 Access Denied");
        view.setStatus(HttpStatus.FORBIDDEN);
        return view;
    }

    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    public ModelAndView handleMaxSizeException(MaxUploadSizeExceededException exc, HttpServletRequest request,
                                               HttpServletResponse response) {
        ModelAndView view = new ModelAndView("redirect:/projects");
        view.addObject("error_message", "Files saving error: " + exc.getMessage());
        view.setStatus(HttpStatus.BAD_REQUEST);
        return view;
    }
}
