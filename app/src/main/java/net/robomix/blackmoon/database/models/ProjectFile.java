package net.robomix.blackmoon.database.models;

import javax.persistence.*;

@Entity
public class ProjectFile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String path;

    @ManyToOne(fetch = FetchType.EAGER)
    private Project project;

    private long dateCreated;

    public ProjectFile(Project project, String path) {
        this.project = project;
        this.path = path;
        this.dateCreated = System.currentTimeMillis();
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
}
