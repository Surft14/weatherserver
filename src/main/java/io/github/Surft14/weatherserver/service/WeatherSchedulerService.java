package io.github.Surft14.weatherserver.service;


import io.github.Surft14.weatherserver.model.City;
import io.github.Surft14.weatherserver.model.WeatherNow;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class WeatherSchedulerService {

    private final WeatherService weatherService;

    private final CityService cityService;

    private final String apiKey = "e484c70c78e84b779ab151237251002";

    @Async("weatherExecutor")
    public void fetchAndSaveWeather(String city, String apiKey){
        System.out.println(LocalDateTime.now() + "  INFO: Weather fetchAndSaveWeather, " + city);
        WeatherNow weatherNow = weatherService.getWeatherNow(city, apiKey);
        weatherService.saveWeatherNow(weatherNow);
    }
    @Async("weatherExecutor")
    @Scheduled(fixedRate = 30 * 60 * 1000)
    public void fetchWeatherPeriodically(){

        List<City> cityList = cityService.getAllCity();
        try {
            for (City city : cityList) {
                System.out.println(LocalDateTime.now() + "  INFO: Weather fetchWeatherPeriodically, " + city);
                fetchAndSaveWeather(city.getName(), apiKey);
            }
        } catch (Exception e) {
            System.err.println("Error at update weather: " + e.getMessage());
        }
    }
}