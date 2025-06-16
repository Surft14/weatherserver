package io.github.Surft14.weatherserver.service.Impl;

import io.github.Surft14.weatherserver.model.City;
import io.github.Surft14.weatherserver.repository.CityRepository;
import io.github.Surft14.weatherserver.service.CityService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Async("weatherExecutor")
@Service
@AllArgsConstructor
@Primary
public class CityServiceImpl implements CityService {

    private CityRepository repository;

    @PostConstruct
    public void init() {
        if (repository.count() == 0) {
            City cityCheboksary = new City();
            cityCheboksary.setName("Cheboksary");
            repository.save(cityCheboksary);
        }
    }

    @Override
    public CompletableFuture<City> findTopByName(String name) {
        return CompletableFuture.completedFuture(repository.findFirstByName(name));
    }

    @Override
    public CompletableFuture<List<City>> getAllCity() {
        return CompletableFuture.completedFuture(repository.getAllCity());
    }

    @Override
    public CompletableFuture<City> saveCity(City city) {
        return CompletableFuture.completedFuture(repository.save(city));
    }

    @Override
    public CompletableFuture<City> updateCity(City city) {
        return CompletableFuture.completedFuture(repository.save(city));
    }

    @Override
    public void deleteCity(City city) {
        repository.delete(city);
    }
}
