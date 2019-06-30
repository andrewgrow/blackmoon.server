package net.robomix.blackmoon.controllers.web;

import net.robomix.blackmoon.database.models.db.Role;
import net.robomix.blackmoon.database.models.db.User;
import net.robomix.blackmoon.database.models.dto.UserDTO;
import net.robomix.blackmoon.service.UserService;
import net.robomix.blackmoon.utils.TextUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static net.robomix.blackmoon.Constants.ERROR_MESSAGE;
import static net.robomix.blackmoon.Constants.INFO_MESSAGE;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userService.getUsersList());
        return "page_user";
    }

    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model) {
        if (user != null) {
            model.addAttribute("user", UserDTO.toDto(user));
            model.addAttribute("roles", Role.values());
        }
        return "page_edit_user";
    }

    @PostMapping
    public String updateUser(@RequestParam("user_id") User user, @RequestParam Map<String, String> form,
                             RedirectAttributes redirectAttributes) {

        if (user == null) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, "User cannot be empty");
            return "redirect:/user";
        }

        // re-store roles
        Set<String> allUsersRoles = Arrays.stream(Role.values()).map(Role::name)
                .collect(Collectors.toSet());
        user.getRoles().clear();
        for (String key : form.keySet()) {
            if (allUsersRoles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }

        boolean isActive = form.containsKey("active") && form.get("active").equals("on");
        user.setUsername(form.get("username"));
        user.setActive(isActive);
        user.setEmail(form.get("email"));
        user.setPhone(form.get("phone"));

        if (form.containsKey("password")) {
            String newPass = form.get("password");
            if (!TextUtils.isEmpty(newPass)) {
                user.setPassword(userService.getPasswordEncoder().encode(newPass));
            }
        }

        userService.saveUser(user);
        redirectAttributes.addFlashAttribute(INFO_MESSAGE, "User has been updated successful");

        return "redirect:/user";
    }

    @PostMapping("delete")
    public String deleteUser(@RequestParam("user_id") User user, @AuthenticationPrincipal UserDTO principal,
                             RedirectAttributes redirectAttributes) {
        if (user == null || principal == null) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, (user == null? "User " : "Principal ") + "cannot be empty");
            return "redirect:/user";
        }

        if (user.getId() == principal.getUserId()) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, "You cannot be delete yourself.");
            return "redirect:/user";
        }

        long deletedUserId = user.getId();
        try {
            userService.deleteUser(user);
        } catch (DataIntegrityViolationException exception) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, "You cannot remove the user because it has projects or files.");
            return "redirect:/user";
        }

        // check delete
        User dbUser = userService.getUserById(deletedUserId);
        if (dbUser == null) {
            redirectAttributes.addFlashAttribute(INFO_MESSAGE, "User has been deleted successful.");
        } else {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, "User has not been deleted. See logs for errors.");
        }

        return "redirect:/user";
    }
}
