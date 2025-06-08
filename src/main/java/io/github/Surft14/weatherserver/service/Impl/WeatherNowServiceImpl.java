package io.github.Surft14.weatherserver.service.Impl;

import io.github.Surft14.weatherserver.model.WeatherHour;
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

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

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
        //http://api.weatherapi.com/v1/forecast.json?key=%s&q=%s&days=1&aqi=no&alerts=no
        String url = String.format("http://api.weatherapi.com/v1/forecast.json?key=%s&q=%s&days=1&aqi=no&alerts=no", apiKey, city);

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

        LocalDateTime time = LocalDateTime.parse(dto.getLocation().getLocaltime(), DATE_TIME_FORMATTER);
        weatherNow.setDateTime(time);

        LocalDateTime lastTime = LocalDateTime.parse(dto.getCurrent().getLast_updated(), DATE_TIME_FORMATTER);
        weatherNow.setLastUpdateTime(lastTime);

        weatherNow.setWeather_text(dto.getCurrent().getCondition().getText());
        weatherNow.setWeather_url(dto.getCurrent().getCondition().getIcon());
        weatherNow.setWeather_code(dto.getCurrent().getCondition().getCode());

        weatherNow.setTemp(dto.getCurrent().getTemp_c());
        weatherNow.setFeelLike(dto.getCurrent().getFeelslike_c());
        weatherNow.setSpeed(dto.getCurrent().getWind_kph());
        weatherNow.setDir(dto.getCurrent().getWind_dir());


        for(WeatherApiResponse.Hour hour : dto.getForecast().getForecastday().get(0).getHour()){
            WeatherHour weatherHour = new WeatherHour();

            weatherHour.setTime(LocalDateTime.parse(hour.getTime(), DATE_TIME_FORMATTER));

            weatherHour.setText(hour.getCondition().getText());
            weatherHour.setIcon(hour.getCondition().getIcon());
            weatherHour.setCode(hour.getCondition().getCode());

            weatherHour.setTemp(hour.getTemp_c());
            weatherHour.setFeelLike(hour.getFeelslike_c());

            weatherHour.setSpeed(hour.getWind_kph());
            weatherHour.setDir(hour.getWind_dir());

            weatherNow.getListHour().add(weatherHour);
        }


        return weatherNow;
    }

}