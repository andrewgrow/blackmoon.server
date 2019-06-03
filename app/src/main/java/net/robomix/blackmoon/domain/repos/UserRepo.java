package net.robomix.blackmoon.domain.repos;

import net.robomix.blackmoon.domain.models.UserModel;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<UserModel, Long> {

}
