package com.officemap.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "buildings")
public class Building {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long buildingId;
    private String buildingName;
    private String buildingAddress;
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City cityName;
    @OneToMany(mappedBy = "building")
    private List<Floor> floors;

    public Building() {
    }

    public City getCity() {
        return cityName;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getBuildingAddress() {
        return buildingAddress;
    }

    public void setBuildingAddress(String buildingAddress) {
        this.buildingAddress = buildingAddress;
    }

    public City getCityName() {
        return cityName;
    }

    public void setCityName(City cityName) {
        this.cityName = cityName;
    }

    public List<Floor> getFloors() {
        return floors;
    }

    public void setFloors(List<Floor> floors) {
        this.floors = floors;
    }
}
