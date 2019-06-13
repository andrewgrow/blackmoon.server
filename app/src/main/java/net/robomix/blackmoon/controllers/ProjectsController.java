package net.robomix.blackmoon.controllers;

import net.robomix.blackmoon.database.models.Project;
import net.robomix.blackmoon.database.models.User;
import net.robomix.blackmoon.service.ProjectService;
import net.robomix.blackmoon.utils.TextUtils;
import net.robomix.blackmoon.utils.Utils;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class ProjectsController implements HandlerExceptionResolver {

    private static final String TAG = ProjectsController.class.getSimpleName();
    private final ProjectService projectService;

    public ProjectsController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/projects")
    public String addNewProject(@RequestParam(value = "file", required = false) MultipartFile[] attachmentsList,
                           @RequestParam(value = "name", required = false) String newProjectName,
                           @RequestParam(value = "short", required = false) String shortDescription,
                           @RequestParam(value = "long", required = false) String longDescription,
                           @AuthenticationPrincipal User user, Map<String, Object> model) {

        System.out.println(TAG + "addNewProject() in thread " + Thread.currentThread().getName());

        List<String> errorsList = new ArrayList<>();

        // check name and user
        if (newProjectName != null && user != null) {

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

        List<Project> projects = projectService.findAll();
        model.put("projects", projects);

        if (errorsList.size() > 0) {
            String error = Utils.convertErrorsListToString(errorsList);
            model.put("error", error);
        }

        return "redirect:/projects";
    }

    @GetMapping("/projects")
    public String projectsPage(Map<String, Object> model) {
        List<Project> projects = projectService.findAll();
        model.put("projects", projects);
        return "projects";
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest request,
                 HttpServletResponse response, Object handler, Exception ex) {
        System.out.println(TAG + "resolveException() in thread " + Thread.currentThread().getName() + ". Message: " + ex.getMessage());
        // stacktrace
        ex.printStackTrace();

        ModelAndView modelAndView = new ModelAndView("projects");
        List<Project> projects = projectService.findAll();
        modelAndView.addObject("projects", projects);
        modelAndView.addObject("error", "New Project was " +
                "not created because an error occurred. Are selected files too long?  You can " +
                "add each file as 2Mb and max 6Mb per time.\n\nError message: " + ex.getMessage());
        return modelAndView;
    }
}
