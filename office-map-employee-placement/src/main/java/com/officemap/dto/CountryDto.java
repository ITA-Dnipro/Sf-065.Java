package com.officemap.dto;

import java.util.List;

public class CountryDto {

    private long countryId;

    private String countryName;

    private List<String> citiesDtoList;

    public CountryDto(long countryId, String countryName, List<String> citiesDtoList) {
        this.countryId = countryId;
        this.countryName = countryName;
        this.citiesDtoList = citiesDtoList;
    }

    public long getCountryId() {
        return countryId;
    }

    public void setCountryId(long countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public List<String> getCitiesDtoList() {
        return citiesDtoList;
    }

    public void setCitiesDtoList(List<String> citiesDtoList) {
        this.citiesDtoList = citiesDtoList;
    }
}
