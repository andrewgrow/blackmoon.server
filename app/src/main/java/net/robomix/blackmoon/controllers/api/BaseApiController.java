package net.robomix.blackmoon.controllers.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.robomix.blackmoon.database.models.dto.ProjectDTO;
import net.robomix.blackmoon.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BaseApiController {

    private final ObjectMapper objectMapper;
    private final ProjectService projectService;

    public BaseApiController(ObjectMapper objectMapper, ProjectService projectService) {
        this.objectMapper = objectMapper;
        this.projectService = projectService;
    }

    @GetMapping("/api/projects")
    public ResponseEntity<Object> testApi() throws JsonProcessingException {
        List<ProjectDTO> projects = projectService.allProjectsAsDTO();
        String result = objectMapper.writeValueAsString(projects);
        ResponseEntity<Object> response = new ResponseEntity<>(result, HttpStatus.OK);
        return response;
    }
}
