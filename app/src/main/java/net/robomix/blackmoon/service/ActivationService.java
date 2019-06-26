package net.robomix.blackmoon.service;

import net.robomix.blackmoon.database.models.db.Activation;
import net.robomix.blackmoon.database.models.db.User;
import net.robomix.blackmoon.database.repos.ActivationRepo;
import net.robomix.blackmoon.utils.TextUtils;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ActivationService {

    private final ActivationRepo activationRepo;

    public ActivationService(ActivationRepo activationRepo) {
        this.activationRepo = activationRepo;
    }

    public String saveNewActivation(User user) {
        String activationCode = UUID.randomUUID().toString();
        Activation activation = new Activation();
        activation.setCode(activationCode);
        activation.setUser(user);
        activation.setDateCreated(System.currentTimeMillis());
        activationRepo.save(activation);
        return activationCode;
    }

    @Nullable
    public Activation findActivationByCode(String code) {
        if (TextUtils.isEmpty(code)) {
            return null;
        }
        return activationRepo.findActivationByCode(code);
    }

    public void save(Activation activation) {
        if (activation == null) {
            return;
        }
        activationRepo.save(activation);
    }
}
