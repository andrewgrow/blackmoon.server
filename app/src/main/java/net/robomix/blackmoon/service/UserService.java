package net.robomix.blackmoon.service;

import net.robomix.blackmoon.database.models.Role;
import net.robomix.blackmoon.database.models.db.User;
import net.robomix.blackmoon.database.models.dto.UserDTO;
import net.robomix.blackmoon.database.repos.UserRepo;
import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

@Service
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User userDB = userRepo.findByUsername(userName);
        if (userDB == null) {
            System.out.println("try to find userName = " + (userName == null ? "null" : userName) + ". But DB returned null.");
            throw new UsernameNotFoundException("User with name = " + userName + " does not found");
        } else {
            System.out.println("SEARCH USER: name = " + userDB.getUsername() + ", role = " + userDB.getRoles());
        }
        return UserDTO.toDto(userDB);
    }

    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public void saveNewUser(UserDTO userDTO) {
        User dbEntity = userRepo.findByUsername(userDTO.getUsername());
        if (dbEntity == null) {
            User user = new User();
            user.setActive(true);
            user.setRoles(Collections.singleton(Role.USER));
            user.setUsername(userDTO.getUsername());
            user.setPassword(getPasswordEncoder().encode(userDTO.getPassword()));
            user.setEmail(userDTO.getEmail());
            user.setPhone(userDTO.getPhone());
            userRepo.save(user);
        }
    }

    public List<UserDTO> getUsersList() {
        List<UserDTO> result = new ArrayList<>();
        userRepo.findAll().forEach(user -> result.add(UserDTO.toDto(user)));
        return result;
    }

    @Nullable
    public User findById(long userId) {
        return userRepo.findById(userId);
    }
}
