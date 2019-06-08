package net.robomix.blackmoon.controllers;

import net.robomix.blackmoon.database.models.ProjectFile;
import net.robomix.blackmoon.database.models.Project;
import net.robomix.blackmoon.database.models.User;
import net.robomix.blackmoon.database.repos.FilesRepo;
import net.robomix.blackmoon.database.repos.ProjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class ProjectsController {

    @Autowired
    private ProjectRepo projectRepo;

    @Autowired
    private FilesRepo filesRepo;

    @Value("${upload.path}")
    private String uploadPath;


    @PostMapping("/projects")
    public String addNewProject(@RequestParam(value = "file", required = false) MultipartFile[] webFilesList,
                           @RequestParam(value = "name", required = false) String newProjectName,
                           @RequestParam(value = "short", required = false) String shortDescription,
                           @RequestParam(value = "long", required = false) String longDescription,
                           @AuthenticationPrincipal User user,
                                   Map<String, Object> model) throws IOException {
        if (newProjectName != null) {
            Project project = new Project();
            project.setName(newProjectName);
            project.setAuthor(user);
            long time = System.currentTimeMillis();
            project.setDateCreated(time);
            project.setDateLastModified(time);
            project.setShortDescription(shortDescription);
            project.setLongDescription(longDescription);
            projectRepo.save(project);

            if (webFilesList != null && webFilesList.length > 0) {
                for (MultipartFile webFile : webFilesList) {
                    if (webFile.isEmpty()) {
                        continue;
                    }
                    File uploadDir = new File(uploadPath);
                    if (!uploadDir.exists()) {
                        uploadDir.mkdir();
                    }
                    String uuid = UUID.randomUUID().toString();
                    String filePath = uploadPath + "/" + uuid + "." + webFile.getOriginalFilename();

                    webFile.transferTo(new File(filePath));

                    ProjectFile projectFile = new ProjectFile(project, filePath);
                    filesRepo.save(projectFile);
                }
            }
        }


        List<Project> projects = projectRepo.findAll();
        model.put("projects", projects);
        return "projects";
    }

    @GetMapping("/projects")
    public String projectsPage(Map<String, Object> model) {
        List<Project> projects = projectRepo.findAll();

//        model.put("message", "with ftl template");
        model.put("projects", projects);
        return "projects";
    }
}
