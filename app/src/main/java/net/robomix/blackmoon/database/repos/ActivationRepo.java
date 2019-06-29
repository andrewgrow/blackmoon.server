package net.robomix.blackmoon.database.repos;

import net.robomix.blackmoon.database.models.db.Activation;
import net.robomix.blackmoon.database.models.db.User;
import org.springframework.data.repository.CrudRepository;

public interface ActivationRepo extends CrudRepository<Activation, Long> {
    Activation findActivationByCode(String code);
    Activation findActivationByUser(User user);
}
