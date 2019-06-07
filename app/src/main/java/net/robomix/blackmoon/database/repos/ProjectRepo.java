package net.robomix.blackmoon.database.repos;

import net.robomix.blackmoon.database.models.Project;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProjectRepo extends CrudRepository<Project, Long> {
    List<Project> findAll();
}
