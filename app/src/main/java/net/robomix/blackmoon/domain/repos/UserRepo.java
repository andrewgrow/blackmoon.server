package net.robomix.blackmoon.domain.repos;

import net.robomix.blackmoon.domain.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Long> {
    User findByUsername(String userName);
}
