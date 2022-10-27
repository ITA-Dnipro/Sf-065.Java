package com.officemap.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cities")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long cityId;
    private String cityName;
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country countryName;
    @OneToMany(mappedBy = "cityName")
    private List<Building> buildings;

    public City() {
    }

    public City(long cityId, String cityName) {
        this.cityId = cityId;
        this.cityName = cityName;
    }

    public long getCityId() {
        return cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public List<Building> getBuildings() {
        return buildings;
    }

    public void setCityId(long cityId) {
        this.cityId = cityId;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setBuildings(List<Building> buildings) {
        this.buildings = buildings;
    }
}
