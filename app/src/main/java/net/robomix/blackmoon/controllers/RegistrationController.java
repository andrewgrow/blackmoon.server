package net.robomix.blackmoon.controllers;

import net.robomix.blackmoon.database.models.db.User;
import net.robomix.blackmoon.service.UserService;
import net.robomix.blackmoon.utils.TextUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registrationPage() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User userDTO,
                          @RequestParam(name = "password_confirm") String passwordConfirm,
                          Map<String, Object> model) {
        User userFromDb = userService.findByUsername(userDTO.getUsername());
        if (userFromDb != null) {
            model.put("error", "User already exists!");
            return "registration";
        }

        if (TextUtils.isEmpty(userDTO.getEmail())) {
            model.put("error", "Email field is empty. Please, add your email.");
            return "registration";
        }

        if (TextUtils.isEmpty(userDTO.getPassword())) {
            model.put("error", "Password field is empty. Please, add your password.");
            return "registration";
        }

        if (!userDTO.getPassword().equals(passwordConfirm)) {
            model.put("error", "Password confirm does not match. " +
                    "Please, add your password and its confirm.");
            return "registration";
        }

        userService.saveNewUser(userDTO);

        model.put("message", "Registration successful. You can login now.");
        model.put("username", userDTO.getUsername());
        return "redirect:/login";
    }
}
