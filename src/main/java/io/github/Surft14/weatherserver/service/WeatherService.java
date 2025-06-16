package io.github.Surft14.weatherserver.service;

import io.github.Surft14.weatherserver.model.WeatherNow;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public interface WeatherService {
    CompletableFuture<WeatherNow> findWeatherNow(String city);

    CompletableFuture<List<WeatherNow>> findByCity(String city);

    CompletableFuture<List<WeatherNow>> findByCityAndDateTime(String city, LocalDateTime dateTime);

    CompletableFuture<WeatherNow> saveWeatherNow(WeatherNow weatherNow);

    CompletableFuture<WeatherNow> updateWeatherNow(WeatherNow weatherNow);

    void deleteWeatherNow(WeatherNow weatherNow);

    CompletableFuture<WeatherNow> getWeatherNow(String city, String apiKey);
}
