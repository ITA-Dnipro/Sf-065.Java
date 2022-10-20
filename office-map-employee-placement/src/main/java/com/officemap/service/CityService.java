package com.officemap.service;

import com.officemap.repositories.CitiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityService {

    private final CitiesRepository citiesRepository;
@Autowired
    public CityService(CitiesRepository citiesRepository) {
        this.citiesRepository = citiesRepository;
    }

}
