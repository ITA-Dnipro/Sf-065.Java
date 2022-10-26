package demo3.entity;

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


    @NotNull
    private Integer numberOfHours;

    private Integer projectId;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    @ManyToOne
    @JoinColumn(name = "projectId", referencedColumnName = "id", updatable = false, insertable = false)
    private Project project;
}
