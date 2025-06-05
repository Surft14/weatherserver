package io.github.Surft14.weatherserver.controller;


import io.github.Surft14.weatherserver.model.WeatherNow;
import io.github.Surft14.weatherserver.service.WeatherService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/weathers_now")
public class WeatherController {

    private WeatherService service;

    @GetMapping("/get/weather_now/{city}&{apiKey}")
    public WeatherNow getWeatherNow(String city, String apiKey){
        WeatherNow weatherNow = service.getWeatherNow(city, apiKey);
        service.saveWeatherNow(weatherNow);
        return weatherNow;
    }

    @GetMapping("/get/weather_now/{city}")
    public WeatherNow findWeatherNow(String city){
        return service.findWeatherNow(city);
    }

    @GetMapping("/get/weathers_now/{city}")
    public List<WeatherNow> findByCity(String city){
        return service.findByCity(city);
    }

    @GetMapping("/get/weathers_now/{city}&{time}")
    public List<WeatherNow> findByCityAndTime(String city, LocalDateTime time){
        return service.findByCityAndDateTime(city, time);
    }

    @PutMapping("/put/save_weather_now")
    public WeatherNow saveWeatherNow(@RequestBody WeatherNow weatherNow){
        return service.saveWeatherNow(weatherNow);
    }

    @PutMapping("/put/update_weather_now")
    public WeatherNow updateWeatherNow( @RequestBody WeatherNow weatherNow){
        return service.updateWeatherNow(weatherNow);
    }

    @DeleteMapping("/delete/weather_now")
    public void deleteWeatherNow(@RequestBody WeatherNow weatherNow){
        service.deleteWeatherNow(weatherNow);
    }


}
