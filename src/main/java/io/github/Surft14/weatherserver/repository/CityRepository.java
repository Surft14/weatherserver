package io.github.Surft14.weatherserver.repository;

import io.github.Surft14.weatherserver.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Long> {
    City findFirstByName(String name);

    @Query("SELECT c FROM City c")
    List<City> getAllCity();
}
