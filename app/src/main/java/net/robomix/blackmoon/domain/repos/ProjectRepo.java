package net.robomix.blackmoon.domain.repos;

import net.robomix.blackmoon.domain.models.ProjectModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProjectRepo extends CrudRepository<ProjectModel, Long> {

    List<ProjectModel> findAll();
}
