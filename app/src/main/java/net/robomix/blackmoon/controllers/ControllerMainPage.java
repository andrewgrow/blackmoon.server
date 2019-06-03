package net.robomix.blackmoon.controllers;

import net.robomix.blackmoon.domain.models.ProjectModel;
import net.robomix.blackmoon.domain.repos.ProjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
public class ControllerMainPage {

    @Autowired
    private ProjectRepo projectRepo;

    @GetMapping
    public String main(Map<String, Object> model) {
        List<ProjectModel> projects = projectRepo.findAll();

        model.put("message", "with ftl template");
        model.put("projects", projects);
        return "main";
    }
}
