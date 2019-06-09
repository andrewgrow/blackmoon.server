package net.robomix.blackmoon.database.models;

import net.robomix.blackmoon.utils.Utils;

import javax.persistence.*;

@Entity
public class ProjectFile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String path;

    @ManyToOne(fetch = FetchType.EAGER)
    private Project project;

    @ManyToOne(fetch = FetchType.EAGER)
    private User author;

    private long dateCreated;

    private String fileHash;

    public ProjectFile(Project project, String path, User author) {
        this.project = project;
        this.path = path;
        this.dateCreated = System.currentTimeMillis();
        this.fileHash = Utils.getHashFromFile(path);
        this.author = author;
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
}
