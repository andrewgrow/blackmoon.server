package net.robomix.blackmoon.controllers.web;

import net.robomix.blackmoon.database.models.dto.UserDTO;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class DefaultPageController {

    @GetMapping("/")
    public String siteEnterPage(@AuthenticationPrincipal UserDTO userDTO, Map<String, Object> model) {
        model.put("user", userDTO);
        return "page_main";
    }
}
