package io.github.Surft14.weatherserver.service.Impl;

import io.github.Surft14.weatherserver.model.City;
import io.github.Surft14.weatherserver.repository.CityRepository;
import io.github.Surft14.weatherserver.service.CityService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
@Primary
public class CityServiceImpl implements CityService {

    private CityRepository repository;

    @PostConstruct
    public void init() {
        if (repository.count() == 0) {
            City cityCheboksary = new City();
            City cityMoscow = new City();
            cityMoscow.setName("Moscow");
            cityCheboksary.setName("Cheboksary");
            repository.save(cityCheboksary);
            repository.save(cityMoscow);
        }
    }

    @Override
    public City findTopByName(String name) {
        return repository.findFirstByName(name);
    }

    @Override
    public List<City> getAllCity() {
        return repository.getAllCity();
    }

    @Override
    public City saveCity(City city) {
        return repository.save(city);
    }

    @Override
    public City updateCity(City city) {
        return repository.save(city);
    }

    @Override
    public void deleteCity(City city) {
        repository.delete(city);
    }
}
