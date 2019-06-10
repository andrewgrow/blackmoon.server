package net.robomix.blackmoon.service;

import net.robomix.blackmoon.database.models.Project;
import net.robomix.blackmoon.database.models.ProjectFile;
import net.robomix.blackmoon.database.models.User;
import net.robomix.blackmoon.database.repos.FilesRepo;
import net.robomix.blackmoon.database.repos.ProjectRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class ProjectService {

    @Value("${upload.path}")
    private String uploadPath;
    private final ProjectRepo projectRepo;
    private final FilesRepo filesRepo;

    public ProjectService(ProjectRepo projectRepo, FilesRepo filesRepo) {
        this.projectRepo = projectRepo;
        this.filesRepo = filesRepo;
    }

    public ProjectRepo repository() {
        return this.projectRepo;
    }


    public List<Project> findAll() {
        return repository().findAll();
    }

    public Project saveNewProject(String projectName, User user, String shortDescription,
                               String longDescription){
        // save new project
        Project project = new Project();
        project.setName(projectName);
        project.setAuthor(user);
        long time = System.currentTimeMillis();
        project.setDateCreated(time);
        project.setDateLastModified(time);
        project.setShortDescription(shortDescription);
        project.setLongDescription(longDescription);
        projectRepo.save(project);
        return project;
    }

    @Nullable
    public String saveNewProjectFiles(Project project, MultipartFile[] attachmentsList, User user){
        // we will return an error if something happens
        String error = null; // or null

        if (user == null) {
            error = "Image was not saved. Author cannot to be empty.";
            return error;
        }

        // check size
        if (attachmentsList != null && attachmentsList.length > 0) {
            // cycle
            for (MultipartFile attachment : attachmentsList) {
                // check empty
                if (attachment.isEmpty()) {
                    error = "Error. Image " + attachment.getOriginalFilename() + " was not saved. " +
                            "Empty image?";
                    continue;
                }

                // make new upload dir (if it does not exist)
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }
                // make random name
                String uuid = UUID.randomUUID().toString();
                String filePath = uploadPath + "/" + uuid + "." + attachment.getOriginalFilename();

                try {
                    // copy to destination file
                    File destinationFile = new File(filePath);
                    attachment.transferTo(destinationFile);

                    // check if copy was successful
                    if (destinationFile.exists()) {
                        // check if it is an image
                        String mimeType = new MimetypesFileTypeMap().getContentType(destinationFile);
                        if (mimeType.startsWith("image/")) {
                            // Well, it's an image.
                            ProjectFile projectFile = new ProjectFile(project, filePath, user, mimeType);
                            filesRepo.save(projectFile);
                        } else {
                            // if it's not an image - delete wrong file
                            destinationFile.delete();
                            error = "Error. Image " + attachment.getOriginalFilename()
                                    + " was not saved. Maybe it was not an image?";
                        }
                    } else {
                        // if copy was break - delete wrong file
                        destinationFile.delete();
                        error = "Error. Image " + attachment.getOriginalFilename()
                                + " was not saved. File does not exist.";
                    }
                } catch (IOException e) {
                    // something went wrong
                    e.printStackTrace();
                    error = "Error. Image " + attachment.getOriginalFilename()
                            + " was not saved. Error:\n"
                    + e.getMessage();
                }
            }
        }
        return error;
    }
}
