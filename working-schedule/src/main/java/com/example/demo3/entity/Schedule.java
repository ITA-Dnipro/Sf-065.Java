package com.example.demo3.entity;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "schedules")
public class Schedule {

    @Id
    @GeneratedValue
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumberOfHours() {
        return numberOfHours;
    }

    public void setNumberOfHours(Integer numberOfHours) {
        this.numberOfHours = numberOfHours;
    }


    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @NotNull
    private Integer numberOfHours;




    @ManyToOne
    @JoinColumn(name = "projectId", referencedColumnName = "id", updatable = false, insertable = false)
    private Project project;
}
