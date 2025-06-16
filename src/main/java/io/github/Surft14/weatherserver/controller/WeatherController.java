package io.github.Surft14.weatherserver.controller;


import io.github.Surft14.weatherserver.model.City;
import io.github.Surft14.weatherserver.model.WeatherNow;
import io.github.Surft14.weatherserver.service.CityService;
import io.github.Surft14.weatherserver.service.WeatherService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

@Async("weatherExecutor")
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/weathers_now")
public class WeatherController {

    private WeatherService weatherService;
    private CityService cityService;

    @GetMapping("/get/fwa/city_apiKey/weather_now")
    ///api/v1/weathers_now/get/fwa/city_apiKey/weather_now?city=London&apiKey=your_api_key
    public CompletableFuture<WeatherNow> getWeatherNow(@RequestParam  String city, @RequestParam  String apiKey){
        System.out.println(LocalDateTime.now() + "  INFO: Controller Weather getWeatherNow, " + city);

        return cityService.findTopByName(city).thenCompose(city1 -> {
            if (city1 == null) {
                City tempCity = new City();
                tempCity.setName(city);
                cityService.saveCity(tempCity); // ❗нужно тоже сделать async
            }

            return weatherService.getWeatherNow(city, apiKey)
                    .thenApply(weather -> {
                        weatherService.saveWeatherNow(weather); // ❗лучше тоже сделать async
                        return weather;
                    });
        });
    }
    ///api/v1/weathers_now/get/db/city/weather_now?city=London
    @GetMapping("/get/db/city/weather_now")
    public CompletableFuture<WeatherNow> findWeatherNow(@RequestParam String city) {
        System.out.println(LocalDateTime.now() + "  INFO: Controller Weather findWeatherNow, " + city);
        return cityService.findTopByName(city).thenCompose(city2 -> {
           if (city2 == null){
               City tempCity = new City();
               tempCity.setName(city);
               cityService.saveCity(tempCity);
           }
           return weatherService.getWeatherNow(city, "e484c70c78e84b779ab151237251002")
                   .thenApply(weatherNow -> {
               weatherService.saveWeatherNow(weatherNow);
               return weatherNow;
           });
        });
    }
    ///api/v1/weathers_now/get/weather_now?city=London
    @GetMapping("/get/db/city/list_weather_now")
    public CompletableFuture<List<WeatherNow>> findByCity(@RequestParam String city) {
        System.out.println(LocalDateTime.now() + "  INFO: Controller Weather findByCity, " + city);
        return weatherService.findByCity(city);
    }

    @GetMapping("/get/db/city_time/list_weather_now")
    ///api/v1/weathers_now/get/db/city_time/list_weather_now?city=London&time=2025-06-04T15:00:00
    public CompletableFuture<List<WeatherNow>> findByCityAndTime(@RequestParam String city, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime time) {
        System.out.println(LocalDateTime.now() + "  INFO: Controller Weather findByCity, " + city + ", " + time);
        return weatherService.findByCityAndDateTime(city, time);
    }

    @PutMapping("/put/db/save/weather_now")
    public CompletableFuture<WeatherNow> saveWeatherNow(@RequestBody WeatherNow weatherNow) {
        System.out.println(LocalDateTime.now() + "  INFO: Controller Weather saveWeatherNow, " + weatherNow);
        return weatherService.saveWeatherNow(weatherNow);
    }

    @PutMapping("/put/db/update/weather_now")
    public CompletableFuture<WeatherNow> updateWeatherNow(@RequestBody WeatherNow weatherNow) {
        System.out.println(LocalDateTime.now() + "  INFO: Controller Weather updateWeatherNow, " + weatherNow);
        return weatherService.updateWeatherNow(weatherNow);
    }

    @DeleteMapping("/delete/db/weather_now")
    public void deleteWeatherNow(@RequestBody WeatherNow weatherNow) {
        System.out.println(LocalDateTime.now() + "  INFO: Controller Weather deleteWeatherNow, " + weatherNow);
        weatherService.deleteWeatherNow(weatherNow);
    }

}
