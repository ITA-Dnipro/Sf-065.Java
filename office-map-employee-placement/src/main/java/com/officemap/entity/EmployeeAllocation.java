package com.officemap.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "employeeAllocation")
public class EmployeeAllocation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long allocationId;
    @CreationTimestamp
    private Date createdAt;
    private Long employeeId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "desk_Id", referencedColumnName = "desk_id")
    private Desk desk;

    public Desk getDesk() {
        return desk;
    }

    public Long getAllocationId() {
        return allocationId;
    }

    public void setAllocationId(Long allocationId) {
        this.allocationId = allocationId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public void setDesk(Desk desk) {
        this.desk = desk;
    }
}
