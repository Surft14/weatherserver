package io.github.Surft14.weatherserver.service;

import io.github.Surft14.weatherserver.model.City;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public interface CityService {
    CompletableFuture<City> findTopByName(String name);

    CompletableFuture<List<City>> getAllCity();

    CompletableFuture<City> saveCity(City city);

    CompletableFuture<City> updateCity(City city);

    void deleteCity(City city);
}
