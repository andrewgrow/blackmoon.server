package net.robomix.blackmoon.domain.repos;

import net.robomix.blackmoon.domain.models.Project;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProjectRepo extends CrudRepository<Project, Long> {
    List<Project> findAll();
}
