package net.robomix.blackmoon.database.models.dto;

import net.robomix.blackmoon.database.models.db.ProjectFile;

public class ProjectFileDTO {

    private long id;
    private String path;
    private String mimeType;
    private long projectId;
    private long authorId;
    private long dateCreated;
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
        dto.setPath(file.getPath());
        dto.setMimeType(file.getMimeType());
        dto.setProjectId(file.getProject().getId());
        dto.setAuthorId(file.getAuthor().getId());
        dto.setDateCreated(file.getDateCreated());
        dto.setFileHash(file.getFileHash());
        return dto;
    }
}
