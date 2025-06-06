package io.github.Surft14.weatherserver.service;


import io.github.Surft14.weatherserver.model.City;
import io.github.Surft14.weatherserver.model.WeatherNow;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class WeatherSchedulerService {

    private final WeatherService weatherService;

    private final CityService cityService;

    private final String city = "Cheboksary";
    private final String apiKey = "e484c70c78e84b779ab151237251002";

    @Scheduled(fixedRate = 30 * 60 * 1000)
    public void fetchWeatherPeriodically(){
        List<City> cityList = cityService.getAllCity();
        try {
            for (City city : cityList) {
                WeatherNow weather = weatherService.getWeatherNow(city.getName(), apiKey);
                weatherService.saveWeatherNow(weather);
            }
            /*WeatherNow weather = weatherService.getWeatherNow(city, apiKey);
            weatherService.saveWeatherNow(weather);*/
        } catch (Exception e) {
            System.err.println("Error at update weather: " + e.getMessage());
        }
    }
}