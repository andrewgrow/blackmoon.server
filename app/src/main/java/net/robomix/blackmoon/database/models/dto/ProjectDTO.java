package net.robomix.blackmoon.database.models.dto;

import net.robomix.blackmoon.database.models.db.Project;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectDTO {

    private long id;
    private String name;
    private String shortDescription;
    private String longDescription;
    private long authorId;
    private long dateCreated;
    private long dateLastModified;
    List<ProjectFileDTO> projectFiles;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public long getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(long dateCreated) {
        this.dateCreated = dateCreated;
    }

    public long getDateLastModified() {
        return dateLastModified;
    }

    public void setDateLastModified(long dateLastModified) {
        this.dateLastModified = dateLastModified;
    }

    public List<ProjectFileDTO> getProjectFiles() {
        return projectFiles;
    }

    public void setProjectFiles(List<ProjectFileDTO> projectFiles) {
        this.projectFiles = projectFiles;
    }

    public static ProjectDTO toDTO(Project project) {
        List<ProjectFileDTO> filesDto = project.getProjectFiles().stream()
                .map(ProjectFileDTO::toDto) // equivalent .map(file -> ProjectFileDTO.toDto(file))
                .collect(Collectors.toList());

        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(project.getId());
        projectDTO.setName(project.getName());
        projectDTO.setAuthorId(project.getAuthor().getId());
        projectDTO.setShortDescription(project.getShortDescription());
        projectDTO.setLongDescription(project.getLongDescription());
        projectDTO.setDateCreated(project.getDateCreated());
        projectDTO.setDateLastModified(project.getDateLastModified());
        projectDTO.setProjectFiles(filesDto);
        return projectDTO;
    }
}
