package io.github.Surft14.weatherserver.repository;

import io.github.Surft14.weatherserver.model.WeatherNow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface WeatherRepository extends JpaRepository<WeatherNow, Long> {
    List<WeatherNow> findByCity(String city);
    List<WeatherNow> findByCityAndDateTime(String city, LocalDateTime dateTime);
    Optional<WeatherNow> getWeatherNow(String city);
}
