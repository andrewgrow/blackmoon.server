package net.robomix.blackmoon.controllers;

import net.robomix.blackmoon.database.models.db.User;
import net.robomix.blackmoon.database.models.dto.UserDTO;
import net.robomix.blackmoon.service.MailService;
import net.robomix.blackmoon.service.UserService;
import net.robomix.blackmoon.utils.TextUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
public class RegistrationController {

    private final UserService userService;
    private final MailService mailService;

    public RegistrationController(UserService userService, MailService mailService) {
        this.userService = userService;
        this.mailService = mailService;
    }

    @GetMapping("/registration")
    public String registrationPage() {
        return "page_registration";
    }

    @PostMapping("/registration")
    public String addUser(UserDTO userDTO, Map<String, Object> model, RedirectAttributes redirectAttributes) {
        User userFromDb = userService.findByUsername(userDTO.getUsername());
        if (userFromDb != null) {
            model.put("error_message", "User already exists!");
            return "page_registration";
        }

        if (TextUtils.isEmpty(userDTO.getEmail())) {
            model.put("error_message", "Email field is empty. Please, add your email.");
            return "page_registration";
        }

        userFromDb = userService.findByEmail(userDTO.getEmail());
        if (userFromDb != null) {
            model.put("error_message", "Email already in use! If it is your email, you can to log in.");
            return "page_registration";
        }

        if (TextUtils.isEmpty(userDTO.getPassword())) {
            model.put("error_message", "Password field is empty. Please, add your password.");
            return "page_registration";
        }

        if (!userDTO.getPassword().equals(userDTO.getMatching())) {
            model.put("error_message", "Password confirm does not match. " +
                    "Please, add your password and its confirm.");
            return "page_registration";
        }

        userService.saveNewUser(userDTO);
        mailService.notifyAdminsAboutUser(userService.getAllAdminsEmail());

        redirectAttributes.addFlashAttribute("info_message", String.format("Hello %s. Your registration is successful. " +
                "But your account will be staying inactive until administration check it.", userDTO.getUsername()));
        redirectAttributes.addFlashAttribute("username", userDTO.getUsername());

        return "redirect:/login";
    }
}
