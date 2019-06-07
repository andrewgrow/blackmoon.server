package net.robomix.blackmoon.database.models;

import javax.persistence.*;

@Entity
@Table(name = "prj")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "_id")
    private long id;

    private String name;
    private String shortDescription;
    private String longDescription;

    @ManyToOne(fetch = FetchType.EAGER)
    private User author;

    private long dateCreated;
    private long dateLastModified;

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

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
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
}
