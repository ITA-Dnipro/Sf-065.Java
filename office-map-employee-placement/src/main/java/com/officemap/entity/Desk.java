package com.officemap.entity;

import javax.persistence.*;

import static com.officemap.common.DeskStatusMessages.AVAILABLE;

@Entity
@Table(name = "desks")
public class Desk {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long deskId;

    private String deskName;
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
    private String status = AVAILABLE;

    @OneToOne(mappedBy = "desk")
    private EmployeeAllocation allocation;

    public Desk() {
    }

    public Desk(Long deskId, String deskName) {
        this.deskId = deskId;
        this.deskName = deskName;
    }

    public Long getDeskId() {
        return deskId;
    }

    public void setDeskId(Long deskId) {
        this.deskId = deskId;
    }

    public String getDeskName() {
        return deskName;
    }

    public void setDeskName(String deskName) {
        this.deskName = deskName;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public EmployeeAllocation getAllocation() {
        return allocation;
    }

    public void setAllocation(EmployeeAllocation allocation) {
        this.allocation = allocation;
    }
}
