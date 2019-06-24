package net.robomix.blackmoon.controllers;

import net.robomix.blackmoon.database.models.db.User;
import net.robomix.blackmoon.database.models.dto.UserDTO;
import net.robomix.blackmoon.service.UserService;
import net.robomix.blackmoon.utils.TextUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registrationPage() {
        return "page_registration";
    }

    @PostMapping("/registration")
    public String addUser(UserDTO userDTO, Map<String, Object> model) {
        User userFromDb = userService.findByUsername(userDTO.getUsername());
        if (userFromDb != null) {
            model.put("error", "User already exists!");
            return "page_registration";
        }

        if (TextUtils.isEmpty(userDTO.getEmail())) {
            model.put("error", "Email field is empty. Please, add your email.");
            return "page_registration";
        }

        userFromDb = userService.findByEmail(userDTO.getEmail());
        if (userFromDb != null) {
            model.put("error", "Email already in use! If it is your email, you can to log in.");
            return "page_registration";
        }

        if (TextUtils.isEmpty(userDTO.getPassword())) {
            model.put("error", "Password field is empty. Please, add your password.");
            return "page_registration";
        }

        if (!userDTO.getPassword().equals(userDTO.getMatching())) {
            model.put("error", "Password confirm does not match. " +
                    "Please, add your password and its confirm.");
            return "page_registration";
        }

        userService.saveNewUser(userDTO);

        model.put("message", "Registration successful. You can login now.");
        model.put("username", userDTO.getUsername());
        return "redirect:/login";
    }
}
