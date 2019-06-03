package net.robomix.blackmoon.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class ControllerMainPage {

    @GetMapping("/")
    public String main(Map<String, Object> model) {
        return "main";
    }
}
