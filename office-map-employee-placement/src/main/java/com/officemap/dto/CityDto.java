package com.officemap.dto;

import java.util.List;

public class CityDto {

    private Long cityId;

    private String cityName;

    private List<String> buildingsDtoList;

    public CityDto(Long cityId, String cityName, List<String> buildingsDtoList) {
        this.cityId = cityId;
        this.cityName = cityName;
        this.buildingsDtoList = buildingsDtoList;
    }

    public CityDto() {
    }

    public Long getCityId() {
        return cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public List<String> getBuildingsDtoList() {
        return buildingsDtoList;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setBuildingsDtoList(List<String> buildingsDtoList) {
        this.buildingsDtoList = buildingsDtoList;
    }
}
