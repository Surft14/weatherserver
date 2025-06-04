package io.github.Surft14.weatherserver.repository;

import io.github.Surft14.weatherserver.model.WeatherNow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface WeatherNowRepository extends JpaRepository<WeatherNow, Long> {
    List<WeatherNow> findByCity(String city);
    List<WeatherNow> findByCityAndDateTime(String city, LocalDateTime dateTime);
}
