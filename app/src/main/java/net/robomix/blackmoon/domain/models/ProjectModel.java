package net.robomix.blackmoon.domain.models;

import javax.persistence.*;

@Entity
@Table(name = "project_model")
public class ProjectModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "_id")
    private long id;

    @Column(name = "project_name")
    private String projectName;

    @Column(name = "project_short_description")
    private String shortDescription;

    @Column(name = "project_long_description")
    private String longDescription;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
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
}
