package io.github.Surft14.weatherserver.service;

import io.github.Surft14.weatherserver.model.City;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CityService {
    City findTopByName(String name);

    List<City> getAllCity();

    City saveCity(City city);

    City updateCity(City city);

    void deleteCity(City city);
}
