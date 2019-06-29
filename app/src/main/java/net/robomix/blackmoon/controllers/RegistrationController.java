package net.robomix.blackmoon.controllers;

import net.robomix.blackmoon.database.models.db.Activation;
import net.robomix.blackmoon.database.models.db.User;
import net.robomix.blackmoon.database.models.dto.UserDTO;
import net.robomix.blackmoon.service.ActivationService;
import net.robomix.blackmoon.service.MailService;
import net.robomix.blackmoon.service.UserService;
import net.robomix.blackmoon.utils.TextUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

import static net.robomix.blackmoon.Constants.ERROR_MESSAGE;
import static net.robomix.blackmoon.Constants.INFO_MESSAGE;

@Controller
public class RegistrationController {

    private final UserService userService;
    private final MailService mailService;
    private final ActivationService activationService;

    public RegistrationController(UserService userService, MailService mailService, ActivationService activationService) {
        this.userService = userService;
        this.mailService = mailService;
        this.activationService = activationService;
    }

    @GetMapping("activation/{code}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String activationPage(@PathVariable String code, Model model) {
        Activation activation = activationService.findActivationByCode(code);
        if (activation == null){
            model.addAttribute(ERROR_MESSAGE, "Activation code is incorrect.");
        } else {
            if (activation.isActivated()) {
                DateTime lastModifiedTime = new DateTime(activation.getDateLastModified());
                DateTimeFormatter fmt = ISODateTimeFormat.dateTime();
                String lastModified = fmt.print(lastModifiedTime);
                model.addAttribute(INFO_MESSAGE, "User has been activated already (" + lastModified + ")");
            } else {
                User user = activation.getUser();
                if (user == null) {
                    model.addAttribute(ERROR_MESSAGE, "User does not exist.");
                } else {
                    user.setActive(true);
                    userService.saveUser(user);

                    activation.setActivated(true);
                    activation.setDateLastModified(System.currentTimeMillis());
                    activationService.save(activation);
                    mailService.notifyUserAboutActivation(user);
                    model.addAttribute(INFO_MESSAGE, "User has been activated successful.");
                }
            }
        }
        return "page_activation";
    }

    @GetMapping("/registration")
    public String registrationPage() {
        return "page_registration";
    }

    @GetMapping("/activation")
    public String activationPage(Model model) {
        model.addAttribute(ERROR_MESSAGE, "You need to select user to activation.");
        return "page_activation";
    }

    @PostMapping("/registration")
    public String addUser(UserDTO userDTO, Map<String, Object> model, RedirectAttributes redirectAttributes) {
        User userFromDb = userService.findByUsername(userDTO.getUsername());
        if (userFromDb != null) {
            model.put(ERROR_MESSAGE, "User already exists!");
            return "page_registration";
        }

        if (TextUtils.isEmpty(userDTO.getEmail())) {
            model.put(ERROR_MESSAGE, "Email field is empty. Please, add your email.");
            return "page_registration";
        }

        userFromDb = userService.findByEmail(userDTO.getEmail());
        if (userFromDb != null) {
            model.put(ERROR_MESSAGE, "Email already in use! If it is your email, you can to log in.");
            return "page_registration";
        }

        if (TextUtils.isEmpty(userDTO.getPassword())) {
            model.put(ERROR_MESSAGE, "Password field is empty. Please, add your password.");
            return "page_registration";
        }

        if (!userDTO.getPassword().equals(userDTO.getMatching())) {
            model.put(ERROR_MESSAGE, "Password confirm does not match. " +
                    "Please, add your password and its confirm.");
            return "page_registration";
        }

        User newUser = userService.saveNewUser(userDTO);
        String activateCode = activationService.saveNewActivation(newUser);
        mailService.notifyAboutNewRegistration(userService.getAllAdminsEmail(), activateCode);

        System.out.println("new user was registered with code " + activateCode);

        redirectAttributes.addFlashAttribute(INFO_MESSAGE, String.format("Hello %s. Your registration is successful. " +
                "But your account will be staying inactive until administration check it.", userDTO.getUsername()));
        redirectAttributes.addFlashAttribute("username", userDTO.getUsername());

        return "redirect:/login";
    }
}
