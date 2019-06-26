package net.robomix.blackmoon.controllers;

import net.robomix.blackmoon.database.models.db.Role;
import net.robomix.blackmoon.database.models.db.User;
import net.robomix.blackmoon.database.models.dto.UserDTO;
import net.robomix.blackmoon.service.UserService;
import net.robomix.blackmoon.utils.TextUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
                             Model model) {

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

        return "redirect:/user";
    }
}
