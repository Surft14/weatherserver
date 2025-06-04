package io.github.Surft14.weatherserver.service.Impl;

import io.github.Surft14.weatherserver.model.WeatherNow;
import io.github.Surft14.weatherserver.repository.WeatherRepository;
import io.github.Surft14.weatherserver.service.WeatherService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Primary
public class WeatherNowServiceImpl implements WeatherService {

    private WeatherRepository repository;

    @Override
    public Optional<WeatherNow> findWeatherNow(String city) {
        return repository.getWeatherNow(city);
    }

    @Override
    public List<WeatherNow> findByCity(String city) {
        return repository.findByCity(city);
    }

    @Override
    public List<WeatherNow> findByCityAndDateTime(String city, LocalDateTime dateTime) {
        return repository.findByCityAndDateTime(city, dateTime);
    }

    @Override
    public WeatherNow saveWeatherNow(WeatherNow weatherNow) {
        return repository.save(weatherNow);
    }

    @Override
    public WeatherNow updateWeatherNow(WeatherNow weatherNow) {
        return repository.save(weatherNow);
    }

    @Override
    public void deleteWeatherNow(WeatherNow weatherNow) {
        repository.delete(weatherNow);
    }

    public WeatherNow getWeatherNow(String city){
        return null;
        //TODO
    }

}
