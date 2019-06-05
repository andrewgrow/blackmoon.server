package net.robomix.blackmoon.controllers;

import net.robomix.blackmoon.domain.models.ProjectModel;
import net.robomix.blackmoon.domain.repos.ProjectRepo;
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
    public String siteEnterPage(Map<String, Object> model) {
        return "site_enter";
    }

    @GetMapping("/projects")
    public String projectsPage(Map<String, Object> model) {
        List<ProjectModel> projects = projectRepo.findAll();

        model.put("message", "with ftl template");
        model.put("projects", projects);
        return "projects";
    }
}
