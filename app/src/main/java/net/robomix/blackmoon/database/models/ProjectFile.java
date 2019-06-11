package net.robomix.blackmoon.database.models;

import net.robomix.blackmoon.utils.Utils;

import javax.persistence.*;

@Entity
public class ProjectFile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(columnDefinition = "TEXT")
    private String path;
    @Column(columnDefinition = "TEXT")
    private String mimeType;

    @ManyToOne(fetch = FetchType.EAGER)
    private Project project;

    @ManyToOne(fetch = FetchType.EAGER)
    private User author;

    private long dateCreated;

    @Column(columnDefinition = "TEXT")
    private String fileHash;

    public ProjectFile() {
    }

    public ProjectFile(Project project, String fullPath, String path, User author, String mimeType) {
        this.project = project;
        this.path = path;
        this.dateCreated = System.currentTimeMillis();
        this.fileHash = Utils.getHashFromFile(fullPath);
        this.author = author;
        this.mimeType = mimeType;
    }

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

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
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

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
}
