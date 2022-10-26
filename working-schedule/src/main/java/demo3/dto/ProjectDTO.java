package demo3.dto;

import com.example.demo3.enums.ProjectType;

import java.util.Date;

public class ProjectDTO {
    private Date startDate;
    private Date endDate;
    private ProjectType status;
    private String name;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public ProjectType getStatus() {
        return status;
    }

    public void setStatus(ProjectType status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
