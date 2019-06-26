package net.robomix.blackmoon.database.repos;

import net.robomix.blackmoon.database.models.db.Activation;
import org.springframework.data.repository.CrudRepository;

public interface ActivationRepo extends CrudRepository<Activation, Long> {
    Activation findActivationByCode(String code);
}
