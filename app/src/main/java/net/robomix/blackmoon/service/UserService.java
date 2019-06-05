package net.robomix.blackmoon.service;

import net.robomix.blackmoon.domain.models.User;
import net.robomix.blackmoon.domain.repos.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
}
