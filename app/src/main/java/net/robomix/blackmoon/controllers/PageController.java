package net.robomix.blackmoon.controllers;

import net.robomix.blackmoon.domain.models.Project;
import net.robomix.blackmoon.domain.models.User;
import net.robomix.blackmoon.domain.repos.ProjectRepo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class PageController {

    private final ProjectRepo projectRepo;

    public PageController(ProjectRepo projectRepo) {
        this.projectRepo = projectRepo;
    }

    @GetMapping("/")
    public String siteEnterPage(@AuthenticationPrincipal User user, Map<String, Object> model) {
        model.put("user", user);
        return "site_enter";
    }

    @GetMapping("/projects")
    public String projectsPage(Map<String, Object> model) {
        List<Project> projects = projectRepo.findAll();

        model.put("message", "with ftl template");
        model.put("projects", projects);
        return "projects";
    }
}
