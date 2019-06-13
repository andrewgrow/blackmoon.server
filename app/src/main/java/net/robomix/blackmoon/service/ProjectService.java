package net.robomix.blackmoon.service;

import net.robomix.blackmoon.database.models.db.Project;
import net.robomix.blackmoon.database.models.db.ProjectFile;
import net.robomix.blackmoon.database.models.db.User;
import net.robomix.blackmoon.database.models.dto.ProjectDTO;
import net.robomix.blackmoon.database.repos.FilesRepo;
import net.robomix.blackmoon.database.repos.ProjectRepo;
import net.robomix.blackmoon.utils.TextUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Value("${upload.path}")
    private String uploadPath;
    private final ProjectRepo projectRepo;
    private final FilesRepo filesRepo;
    private static final String TAG = ProjectService.class.getSimpleName();

    public ProjectService(ProjectRepo projectRepo, FilesRepo filesRepo) {
        this.projectRepo = projectRepo;
        this.filesRepo = filesRepo;
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

                if (TextUtils.isEmpty(attachment.getOriginalFilename())) {
                    continue;
                }

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
                String shortPath = uuid + "." + attachment.getOriginalFilename();
                String fullPath = getFullPath(shortPath);

                try {
                    // copy to destination file
                    File destinationFile = new File(fullPath);
                    attachment.transferTo(destinationFile);

                    // check if copy was successful
                    if (destinationFile.exists()) {
                        // check if it is an image
                        String mimeType = new MimetypesFileTypeMap().getContentType(destinationFile);
                        if (mimeType.startsWith("image/")) {
                            // Well, it's an image.
                            ProjectFile projectFile = new ProjectFile(project, fullPath, shortPath, user, mimeType);
                            filesRepo.save(projectFile);
                            // remove it if the file has the same duplicate
                            removeDuplicate(projectFile.getFileHash(), shortPath);
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

    private String getFullPath(String shortPath){
        return uploadPath + "/" + shortPath;
    }

    private void removeDuplicate(String fileHash, String shortPath) {
        List<ProjectFile> fileList = filesRepo.findAllByFileHash(fileHash);
        if (fileList != null && fileList.size() > 1) {
            // remove all copies but change full path
            String fullPath;
            File file;
            for (ProjectFile projectFile : fileList) {
                String filePath = projectFile.getPath();
                if (!filePath.equals(shortPath)) {
                    fullPath = getFullPath(projectFile.getPath());
                    file = new File(fullPath);
                    file.delete();
                    projectFile.setPath(shortPath);
                    filesRepo.save(projectFile);
                }
            }
        }
    }

    public List<ProjectDTO> allProjectsAsDTO() {
        return projectRepo.findAll().stream().map(ProjectDTO::toDTO).collect(Collectors.toList());
    }
}
