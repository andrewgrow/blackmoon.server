package net.robomix.blackmoon.service;

import net.robomix.blackmoon.database.models.Role;
import net.robomix.blackmoon.database.models.User;
import net.robomix.blackmoon.database.repos.UserRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userDB = userRepo.findByUsername(username);
        System.out.println("SEARCH USER: name = " + userDB.getUsername() + ", role = " + userDB.getRoles());
        return userDB;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public void saveNewUser(User userWebForm) {
        User userDB = userRepo.findByUsername(userWebForm.getUsername());
        if (userDB == null) {
            User user = new User();
            user.setActive(true);
            user.setRoles(Collections.singleton(Role.USER));
            user.setUsername(userWebForm.getUsername());
            user.setPassword(passwordEncoder().encode(userWebForm.getPassword()));
            user.setEmail(userWebForm.getEmail());
            user.setPhone(userWebForm.getPhone());
            userRepo.save(user);
        }
    }
}
