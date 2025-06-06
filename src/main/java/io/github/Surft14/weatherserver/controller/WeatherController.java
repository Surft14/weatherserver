package io.github.Surft14.weatherserver.controller;


import io.github.Surft14.weatherserver.model.WeatherNow;
import io.github.Surft14.weatherserver.service.WeatherService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/weathers_now")
public class WeatherController {

    private WeatherService service;

    @GetMapping("/get/fwa/city_apiKey/weather_now")
    ///api/v1/weathers_now/get/fwa/city_apiKey/weather_now?city=London&apiKey=your_api_key
    public WeatherNow getWeatherNow(@RequestParam  String city, @RequestParam  String apiKey){
        WeatherNow weatherNow = service.getWeatherNow(city, apiKey);
        service.saveWeatherNow(weatherNow);
        return weatherNow;
    }
    ///api/v1/weathers_now/get/db/city/weather_now?city=London
    @GetMapping("/get/db/city/weather_now")
    public WeatherNow findWeatherNow(@RequestParam String city) {
        return service.findWeatherNow(city);
    }
    ///api/v1/weathers_now/get/weather_now?city=London
    @GetMapping("/get/db/city/list_weather_now")
    public List<WeatherNow> findByCity(@RequestParam String city) {
        return service.findByCity(city);
    }

    @GetMapping("/get/db/city_time/list_weather_now")
    ///api/v1/weathers_now/get/db/city_time/list_weather_now?city=London&time=2025-06-04T15:00:00
    public List<WeatherNow> findByCityAndTime(@RequestParam String city, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime time) {
        return service.findByCityAndDateTime(city, time);
    }

    @PutMapping("/put/db/save_weather_now")
    public WeatherNow saveWeatherNow(@RequestBody WeatherNow weatherNow) {
        return service.saveWeatherNow(weatherNow);
    }

    @PutMapping("/put/db/update_weather_now")
    public WeatherNow updateWeatherNow(@RequestBody WeatherNow weatherNow) {
        return service.updateWeatherNow(weatherNow);
    }

    @DeleteMapping("/delete/db/weather_now")
    public void deleteWeatherNow(@RequestBody WeatherNow weatherNow) {
        service.deleteWeatherNow(weatherNow);
    }

}
