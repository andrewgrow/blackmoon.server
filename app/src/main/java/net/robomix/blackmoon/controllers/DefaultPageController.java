package net.robomix.blackmoon.controllers;

import net.robomix.blackmoon.database.models.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class DefaultPageController {

    @GetMapping("/")
    public String siteEnterPage(@AuthenticationPrincipal User user, Map<String, Object> model) {
        model.put("user", user);
        return "site_enter";
    }
}
