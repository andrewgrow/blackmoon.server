package net.robomix.blackmoon.database.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.robomix.blackmoon.Constants;
import net.robomix.blackmoon.database.models.db.ProjectFile;

public class ProjectFileDTO {

    private String path;

    @JsonIgnore
    private long id;
    @JsonIgnore
    private String mimeType;
    @JsonIgnore
    private long projectId;
    @JsonIgnore
    private long authorId;
    @JsonIgnore
    private long dateCreated;
    @JsonIgnore
    private String fileHash;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
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

    public String getFileHash() {
        return fileHash;
    }

    public void setFileHash(String fileHash) {
        this.fileHash = fileHash;
    }

    public static ProjectFileDTO toDto(ProjectFile file) {
        ProjectFileDTO dto = new ProjectFileDTO();
        dto.setId(file.getId());
        dto.setPath("http://" + Constants.getHostName() + "/img/" + file.getPath());
        dto.setMimeType(file.getMimeType());
        dto.setProjectId(file.getProject().getId());
        dto.setAuthorId(file.getAuthor().getId());
        dto.setDateCreated(file.getDateCreated());
        dto.setFileHash(file.getFileHash());
        return dto;
    }
}
