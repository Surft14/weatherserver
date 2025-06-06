package io.github.Surft14.weatherserver.controller;


import io.github.Surft14.weatherserver.model.City;
import io.github.Surft14.weatherserver.service.CityService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/city_table")
public class CityController {

    private CityService cityService;

    @GetMapping("/get/db/city/city_table")
    public City getCity(@RequestParam String city){
        return cityService.findTopByName(city);
    }

    @GetMapping("/get/db/city_table")
    public List<City> getAllCity(){
        return cityService.getAllCity();
    }

    @PutMapping("/put/db/save/city_table")
    public City saveCity(@RequestBody City city){
        return cityService.saveCity(city);
    }

    @PutMapping("/put/db/update/city_table")
    public City updateCity(@RequestBody City city){
        return cityService.updateCity(city);
    }

    @DeleteMapping("/delete/db/city_table")
    public void deleteCity(@RequestBody City city){
        cityService.deleteCity(city);
    }

}
