package net.robomix.blackmoon.database.repos;

import net.robomix.blackmoon.database.models.ProjectFile;
import org.springframework.data.repository.CrudRepository;

public interface FilesRepo extends CrudRepository<ProjectFile, Long> {
}
