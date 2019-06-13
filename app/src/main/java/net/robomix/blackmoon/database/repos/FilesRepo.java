package net.robomix.blackmoon.database.repos;

import net.robomix.blackmoon.database.models.db.Project;
import net.robomix.blackmoon.database.models.db.ProjectFile;
import net.robomix.blackmoon.database.models.db.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FilesRepo extends CrudRepository<ProjectFile, Long> {
    List<ProjectFile> findAll();
    List<ProjectFile> findAllByAuthor(User author);
    List<ProjectFile> findAllByProject(Project project);
    List<ProjectFile> findAllByFileHash(String hash);
}
