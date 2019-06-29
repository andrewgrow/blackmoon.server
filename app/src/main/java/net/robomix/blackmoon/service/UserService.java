package net.robomix.blackmoon.service;

import net.robomix.blackmoon.database.models.db.Activation;
import net.robomix.blackmoon.database.models.db.Role;
import net.robomix.blackmoon.database.models.db.User;
import net.robomix.blackmoon.database.models.dto.UserDTO;
import net.robomix.blackmoon.database.repos.ActivationRepo;
import net.robomix.blackmoon.database.repos.UserRepo;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final ActivationRepo activationRepo;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder, ActivationRepo activationRepo) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.activationRepo = activationRepo;
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

    public User saveNewUser(UserDTO userDTO) {
        User user = userRepo.findByUsername(userDTO.getUsername());
        if (user == null) {
            user = new User();
            user.setActive(false); // account will be staying inactive until administration check it
            user.setRoles(Collections.singleton(Role.USER));
            user.setUsername(userDTO.getUsername());
            user.setPassword(getPasswordEncoder().encode(userDTO.getPassword()));
            user.setEmail(userDTO.getEmail());
            user.setPhone(userDTO.getPhone());
            userRepo.save(user);
        }
        return user;
    }

    public List<UserDTO> getUsersList() {
        List<UserDTO> result = new ArrayList<>();
        userRepo.findAll().forEach(user -> result.add(UserDTO.toDto(user)));
        return result;
    }

    public void saveUser(User user) {
        if (user == null) {
            return;
        }
        userRepo.save(user);
    }

    public List<String> getAllAdminsEmail() {
        return userRepo.getUsersByRoles(Role.ADMIN).stream().map(User::getEmail).collect(Collectors.toList());
    }

    public void deleteUser(User user) throws DataIntegrityViolationException {
        if (user == null) {
            return;
        }
        Activation activation = activationRepo.findActivationByUser(user);
        if (activation != null) {
            activationRepo.delete(activation);
        }
        userRepo.delete(user);
    }

    @Nullable
    public User getUserById(long deletedUserId) {
        return userRepo.findById(deletedUserId);
    }
}
