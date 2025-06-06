package io.github.Surft14.weatherserver.controller;


import io.github.Surft14.weatherserver.model.City;
import io.github.Surft14.weatherserver.model.WeatherNow;
import io.github.Surft14.weatherserver.service.CityService;
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

    private WeatherService weatherService;
    private CityService cityService;


    @GetMapping("/get/db/city/city_table")
    public City getCity(@RequestParam String city){
        return cityService.findTopByName(city);
    }

    @GetMapping("/get/db/city_table")
    public List<City> getAllCity(){
        return cityService.getAllCity();
    }

    @GetMapping("/get/fwa/city_apiKey/weather_now")
    ///api/v1/weathers_now/get/fwa/city_apiKey/weather_now?city=London&apiKey=your_api_key
    public WeatherNow getWeatherNow(@RequestParam  String city, @RequestParam  String apiKey){
        City city1 = cityService.findTopByName(city);
        if (city1 == null){
            City tempCity = new City();
            tempCity.setName(city);
            cityService.saveCity(tempCity);
        }
        WeatherNow weatherNow = weatherService.getWeatherNow(city, apiKey);
        weatherService.saveWeatherNow(weatherNow);
        return weatherNow;
    }
    ///api/v1/weathers_now/get/db/city/weather_now?city=London
    @GetMapping("/get/db/city/weather_now")
    public WeatherNow findWeatherNow(@RequestParam String city) {
        City city1 = cityService.findTopByName(city);
        if (city1 == null){
            City tempCity = new City();
            tempCity.setName(city);
            cityService.saveCity(tempCity);
            WeatherNow now = weatherService.getWeatherNow(city, "e484c70c78e84b779ab151237251002");
            weatherService.saveWeatherNow(now);
        }
        return weatherService.findWeatherNow(city);
    }
    ///api/v1/weathers_now/get/weather_now?city=London
    @GetMapping("/get/db/city/list_weather_now")
    public List<WeatherNow> findByCity(@RequestParam String city) {
        return weatherService.findByCity(city);
    }

    @GetMapping("/get/db/city_time/list_weather_now")
    ///api/v1/weathers_now/get/db/city_time/list_weather_now?city=London&time=2025-06-04T15:00:00
    public List<WeatherNow> findByCityAndTime(@RequestParam String city, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime time) {
        return weatherService.findByCityAndDateTime(city, time);
    }

    @PutMapping("/put/db/save/city_table")
    public City saveCity(@RequestBody City city){
        return cityService.saveCity(city);
    }

    @PutMapping("/put/db/update/city_table")
    public City updateCity(@RequestBody City city){
        return cityService.updateCity(city);
    }

    @PutMapping("/put/db/save/weather_now")
    public WeatherNow saveWeatherNow(@RequestBody WeatherNow weatherNow) {
        return weatherService.saveWeatherNow(weatherNow);
    }

    @PutMapping("/put/db/update/weather_now")
    public WeatherNow updateWeatherNow(@RequestBody WeatherNow weatherNow) {
        return weatherService.updateWeatherNow(weatherNow);
    }

    @DeleteMapping("/delete/db/city_table")
    public void deleteCity(@RequestBody City city){
        cityService.deleteCity(city);
    }

    @DeleteMapping("/delete/db/weather_now")
    public void deleteWeatherNow(@RequestBody WeatherNow weatherNow) {
        weatherService.deleteWeatherNow(weatherNow);
    }

}
