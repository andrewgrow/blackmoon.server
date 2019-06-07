package net.robomix.blackmoon.database.repos;

import net.robomix.blackmoon.database.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Long> {
    User findByUsername(String userName);
}
