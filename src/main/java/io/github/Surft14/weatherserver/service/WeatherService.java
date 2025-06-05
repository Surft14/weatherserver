package io.github.Surft14.weatherserver.service;

import io.github.Surft14.weatherserver.model.WeatherNow;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public interface WeatherService {
    WeatherNow findWeatherNow(String city);
    List<WeatherNow> findByCity(String city);
    List<WeatherNow> findByCityAndDateTime(String city, LocalDateTime dateTime);
    WeatherNow saveWeatherNow(WeatherNow weatherNow);
    WeatherNow updateWeatherNow(WeatherNow weatherNow);
    void deleteWeatherNow(WeatherNow weatherNow);
    WeatherNow getWeatherNow(String city, String apiKey);
}
