package net.robomix.blackmoon.controllers;

import net.robomix.blackmoon.database.models.db.Project;
import net.robomix.blackmoon.database.models.db.ProjectFile;
import net.robomix.blackmoon.database.models.db.User;
import net.robomix.blackmoon.database.models.dto.ProjectDTO;
import net.robomix.blackmoon.database.models.dto.UserDTO;
import net.robomix.blackmoon.service.MailService;
import net.robomix.blackmoon.service.ProjectService;
import net.robomix.blackmoon.service.UserService;
import net.robomix.blackmoon.utils.TextUtils;
import net.robomix.blackmoon.utils.Utils;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static net.robomix.blackmoon.Constants.ERROR_MESSAGE;
import static net.robomix.blackmoon.Constants.INFO_MESSAGE;

@Controller
public class ProjectsController implements HandlerExceptionResolver {

    private static final String TAG = ProjectsController.class.getSimpleName();
    private final ProjectService projectService;
    private final UserService userService;
    private final MailService mailService;

    public ProjectsController(ProjectService projectService, UserService userService,
                              MailService mailService) {
        this.projectService = projectService;
        this.userService = userService;
        this.mailService = mailService;
    }

    @PostMapping("/projects")
    public String addNewProject(@RequestParam(value = "file", required = false) MultipartFile[] attachmentsList,
                                @RequestParam(value = "name", required = false) String newProjectName,
                                @RequestParam(value = "short", required = false) String shortDescription,
                                @RequestParam(value = "long", required = false) String longDescription,
                                RedirectAttributes redirectAttributes,
                                @AuthenticationPrincipal UserDTO userDTO,
                                Map<String, Object> model) {

        System.out.println(TAG + ": addNewProject() in thread " + Thread.currentThread().getName());

        User user = userService.findByUsername(userDTO.getUsername());

        List<String> errorsList = new ArrayList<>();

        // check name and user
        if (!TextUtils.isEmpty(newProjectName) && user != null) {

            // save new project
            Project project = projectService.saveNewProject(newProjectName, user,
                    shortDescription, longDescription);

            if (project == null) {
                errorsList.add("Sorry, but project was not saved. Something went wrong.");
            } else {
                // save files for project
                String errorWhileSave = projectService.saveNewProjectFiles(project,
                        attachmentsList, user);

                // if we have an error after saving we will show it
                if (!TextUtils.isEmpty(errorWhileSave)) {
                    errorsList.add(errorWhileSave);
                }
            }
        } else {
            // error: we do not have a new project name or we have an empty user
            errorsList.add("Sorry, but you have to set a name for the project.");
        }

        List<ProjectDTO> projects = projectService.allProjectsAsDTO();
        model.put("projects", projects);

        if (errorsList.size() > 0) {
            String error = Utils.convertErrorsListToString(errorsList);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, error);
        } else {
            mailService.notifyNewProject();
        }

        return "redirect:/projects";
    }

    @GetMapping("/projects")
    public String projectsPage(Map<String, Object> model, @AuthenticationPrincipal UserDTO userDTO) {
        List<ProjectDTO> projects = projectService.allProjectsAsDTO();
        model.put("projects", projects);
        model.put("user", userDTO);
        return "page_projects";
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest request,
                 HttpServletResponse response, Object handler, Exception ex) {
        System.out.println(TAG + "resolveException() in thread " + Thread.currentThread().getName() + ". Message: " + ex.getMessage());
        // stacktrace
        ex.printStackTrace();

        ModelAndView modelAndView = new ModelAndView("page_projects");
        List<ProjectDTO> projects = projectService.allProjectsAsDTO();
        modelAndView.addObject("projects", projects);
        modelAndView.addObject(ERROR_MESSAGE, "Error message: " + ex.getMessage());
        return modelAndView;
    }

    @GetMapping("/projects/{project}")
    public String editProjectPage(@PathVariable Project project, Model model) {
        if (project == null) {
            model.addAttribute(ERROR_MESSAGE, "Project must be not null.");
        } else {
            model.addAttribute("project", ProjectDTO.toDTO(project));
        }
        return "page_edit_project";
    }

    @PostMapping("/projects/edit")
    public String updateProject(@RequestParam("project_id") long projectId,
                                @RequestParam(value = "file", required = false) MultipartFile[] attachmentsList,
                                @RequestParam Map<String, String> form,
                                @AuthenticationPrincipal UserDTO userDTO,
                                RedirectAttributes redirectAttributes) {
        Project project = projectService.getProjectById(projectId);
        if (project == null) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE,
                    "Project with id " + projectId + " does not exist.");
        } else {
            String name = form.get("name");
            if (TextUtils.isEmpty(name.trim())) {
                redirectAttributes.addFlashAttribute(ERROR_MESSAGE,
                        "Project must have a name.");
                return "redirect:/projects/" + projectId;
            }
            project.setName(name);
            String shortDescr = form.get("short_description");
            project.setShortDescription(shortDescr);
            String longDescr = form.get("long_description");
            project.setLongDescription(longDescr);
            project.setDateLastModified(System.currentTimeMillis());
            projectService.save(project);

            // save files for project
            String errorWhileSave = projectService.saveNewProjectFiles(project,
                    attachmentsList, userService.findByUsername(userDTO.getUsername()));

            // if we have an error after saving we will show it
            if (!TextUtils.isEmpty(errorWhileSave)) {
                redirectAttributes.addFlashAttribute(ERROR_MESSAGE,
                        errorWhileSave);
            } else {
                redirectAttributes.addFlashAttribute(INFO_MESSAGE,
                        "Project has been updated successful.");
            }
        }
        redirectAttributes.addFlashAttribute("projects",
                projectService.allProjectsAsDTO());
        return "redirect:/projects";
    }

    @PostMapping("/projects/delete_photo")
    public String deletePhotoFromProject(@RequestParam("project_id") long projectId,
                                         @RequestParam("photo_id") long photoId,
                                         RedirectAttributes redirectAttributes) {
        if (projectId == 0 || photoId == 0) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, "You need to have projectId and photoId");
            return "redirect:/projects/" + projectId;
        }

        ProjectFile file = projectService.getFileById(photoId);
        if (file == null) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, "File with id " + photoId + " not found.");
            return "redirect:/projects/" + projectId;
        }

        projectService.removeFile(file);
        redirectAttributes.addFlashAttribute(INFO_MESSAGE, "File has been removed successful");

        return "redirect:/projects/" + projectId;
    }

    @PostMapping("projects/delete")
    public String deleteProject(@RequestParam("project_id") long projectId,
                                RedirectAttributes redirectAttributes) {
        if (projectId == 0) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, "You need to have projectId.");
            return "redirect:/projects";
        }

        projectService.removeProjectWithFiles(projectId);
        redirectAttributes.addFlashAttribute(INFO_MESSAGE, "Project has been removed successful");

        return "redirect:/projects";
    }
}
