package net.robomix.blackmoon.database.repos;

import net.robomix.blackmoon.database.models.Role;
import net.robomix.blackmoon.database.models.db.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepo extends CrudRepository<User, Long> {
    User findByUsername(String userName);
    User findByEmail(String email);
    User findById(long id);
    List<User> getUsersByRoles(Role role);
}
