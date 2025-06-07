package io.github.Surft14.weatherserver.service.Impl;

import io.github.Surft14.weatherserver.model.api.WeatherApiResponse;
import io.github.Surft14.weatherserver.model.WeatherNow;
import io.github.Surft14.weatherserver.repository.WeatherRepository;
import io.github.Surft14.weatherserver.service.WeatherService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@AllArgsConstructor
@Primary
public class WeatherNowServiceImpl implements WeatherService {

    private WeatherRepository repository;

    private final WebClient webClient = WebClient.create();

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


    @Override
    public WeatherNow findWeatherNow(String city) {
        return repository.findTopByCityOrderByDateTimeDesc(city);
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

    @Override
    public WeatherNow getWeatherNow(String city, String apiKey) {

        String url = String.format("http://api.weatherapi.com/v1/current.json?key=%s&q=%s&aqi=no", apiKey, city);

        Mono<WeatherApiResponse> responseMono = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(WeatherApiResponse.class);

        WeatherApiResponse dto = responseMono.block();

        if (dto == null) {
            throw new RuntimeException("Получен пустой ответ от WeatherAPI");
        }

        WeatherNow weatherNow = new WeatherNow();

        weatherNow.setCity(dto.getLocation().getName());
        weatherNow.setRegion(dto.getLocation().getRegion());
        weatherNow.setCountry(dto.getLocation().getCountry());

        LocalDateTime time = LocalDateTime.parse(dto.getLocation().getLocaltime(), FORMATTER);
        weatherNow.setDateTime(time);

        LocalDateTime lastTime = LocalDateTime.parse(dto.getCurrent().getLastUpdate(), FORMATTER);
        weatherNow.setLastUpdateTime(lastTime);

        weatherNow.setWeather_text(dto.getCurrent().getCondition().getText());
        weatherNow.setWeather_url(dto.getCurrent().getCondition().getIcon());
        weatherNow.setWeather_code(dto.getCurrent().getCondition().getCode());

        weatherNow.setTemp(dto.getCurrent().getTempC());
        weatherNow.setFeelLike(dto.getCurrent().getFeelslikeC());
        weatherNow.setSpeed(dto.getCurrent().getWindKph());
        weatherNow.setDir(dto.getCurrent().getWindDir());


        return weatherNow;
    }

}