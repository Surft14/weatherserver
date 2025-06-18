package io.github.Surft14.weatherserver.service.Impl;

import io.github.Surft14.weatherserver.model.WeatherForecast;
import io.github.Surft14.weatherserver.model.WeatherHour;
import io.github.Surft14.weatherserver.model.WeatherNow;
import io.github.Surft14.weatherserver.model.api.WeatherApiResponse;
import io.github.Surft14.weatherserver.repository.WeatherRepository;
import io.github.Surft14.weatherserver.service.WeatherService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Async("weatherExecutor")
@Service
@AllArgsConstructor
@Primary
public class WeatherNowServiceImpl implements WeatherService {

    private WeatherRepository repository;

    private final WebClient webClient = WebClient.create();


    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    public CompletableFuture<WeatherNow> findWeatherNow(String city) {
        System.out.println(LocalDateTime.now() + "  INFO: Service Weather findWeatherNow, " + city);
        return CompletableFuture.completedFuture(repository.findTopByCityOrderByDateTimeDesc(city));
    }

    @Override
    public CompletableFuture<List<WeatherNow>> findByCity(String city) {
        System.out.println(LocalDateTime.now() + "  INFO: Service Weather findByCity, " + city);
        return CompletableFuture.completedFuture(repository.findByCity(city));
    }

    @Override
    public CompletableFuture<List<WeatherNow>> findByCityAndDateTime(String city, LocalDateTime dateTime) {
        System.out.println(LocalDateTime.now() + "  INFO: Service Weather findByCityAndDateTime, " + city + ", " + dateTime);
        return CompletableFuture.completedFuture(repository.findByCityAndDateTime(city, dateTime));
    }

    @Override
    public CompletableFuture<WeatherNow> saveWeatherNow(WeatherNow weatherNow) {
        System.out.println(LocalDateTime.now() + "  INFO: Service Weather save, " + weatherNow.getCity());
        return CompletableFuture.completedFuture(repository.save(weatherNow));
    }

    @Override
    public CompletableFuture<WeatherNow> updateWeatherNow(WeatherNow weatherNow) {
        System.out.println(LocalDateTime.now() + "  INFO: Service Weather update, " + weatherNow.getCity());
        return CompletableFuture.completedFuture(repository.save(weatherNow));
    }

    @Override
    public void deleteWeatherNow(WeatherNow weatherNow) {
        System.out.println(LocalDateTime.now() + "  INFO: Service Weather delete, " + weatherNow.getCity());
        repository.delete(weatherNow);
    }
    @Async("weatherExecutor")
    @Override
    public CompletableFuture<WeatherNow> getWeatherNow(String city, String apiKey) {
        System.out.println(LocalDateTime.now() + "  INFO: Service Weather getWeatherNow start, " + city);

        String url = String.format(
                "http://api.weatherapi.com/v1/forecast.json?key=%s&q=%s&days=4&aqi=no&alerts=no",
                apiKey, city
        );

        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(WeatherApiResponse.class)
                .timeout(Duration.ofSeconds(30))
                .toFuture()
                .thenCompose(dto -> {
                    if (dto == null) {
                        throw new RuntimeException("Получен пустой ответ от WeatherAPI");
                    }

                    WeatherNow weatherNow = new WeatherNow();

                    weatherNow.setCity(dto.getLocation().getName());
                    weatherNow.setRegion(dto.getLocation().getRegion());
                    weatherNow.setCountry(dto.getLocation().getCountry());
                    System.out.println(LocalDateTime.now() + "  INFO: Service Weather getWeatherNow, " +
                            weatherNow.getCity() + " " + weatherNow.getRegion() + " " + weatherNow.getCountry());

                    LocalDateTime time = LocalDateTime.parse(dto.getLocation().getLocaltime(), DATE_TIME_FORMATTER);
                    weatherNow.setDateTime(time);
                    System.out.println(LocalDateTime.now() + "  INFO: Service Weather getWeatherNow, " + weatherNow.getDateTime());

                    LocalDateTime lastTime = LocalDateTime.parse(dto.getCurrent().getLast_updated(), DATE_TIME_FORMATTER);
                    weatherNow.setLastUpdateTime(lastTime);
                    System.out.println(LocalDateTime.now() + "  INFO: Service Weather getWeatherNow, " + weatherNow.getLastUpdateTime());

                    weatherNow.setText(dto.getCurrent().getCondition().getText());
                    weatherNow.setIcon(dto.getCurrent().getCondition().getIcon());
                    weatherNow.setCode(dto.getCurrent().getCondition().getCode());
                    System.out.println(LocalDateTime.now() + "  INFO: Service Weather getWeatherNow, " +
                            weatherNow.getText() + " " + weatherNow.getIcon() + " " + weatherNow.getCode());

                    weatherNow.setTemp(dto.getCurrent().getTemp_c());
                    weatherNow.setFeelLike(dto.getCurrent().getFeelslike_c());
                    weatherNow.setSpeed(dto.getCurrent().getWind_kph());
                    weatherNow.setDir(dto.getCurrent().getWind_dir());
                    System.out.println(LocalDateTime.now() + "  INFO: Service Weather getWeatherNow, " +
                            weatherNow.getFeelLike() + " " + weatherNow.getSpeed() + " " + weatherNow.getDir());

                    System.out.println(LocalDateTime.now() + "  INFO: Service Weather getWeatherNow end, " + city);

                    CompletableFuture<List<WeatherHour>> hoursFuture = getListWeatherHour(dto);
                    CompletableFuture<List<WeatherForecast>> forecastFuture = getListWeatherForecast(dto);


                    return hoursFuture.thenCombine(forecastFuture, (hours, forecasts) -> {
                        weatherNow.setListHour(hours);
                        weatherNow.setWeathersList(forecasts);
                        return weatherNow;
                    });
                })
                .exceptionally(e -> {
                    System.out.println(LocalDateTime.now() + "  ERROR: Service Weather getWeatherNow, " + city +
                            " Error: " + e.getMessage());
                    return null;
                });
    }

    @Async("weatherExecutor")
    @Override
    public CompletableFuture<List<WeatherHour>> getListWeatherHour(WeatherApiResponse dto){
        List<WeatherHour> list = new ArrayList<WeatherHour>();
        int i = 0;
        for(WeatherApiResponse.Hour hour : dto.getForecast().getForecastday().get(0).getHour()){
            i++;
            System.out.println(LocalDateTime.now() + "  INFO: Hour " + i + ", " + dto.getLocation().getName());
            WeatherHour weatherHour = new WeatherHour();

            LocalDateTime localDateTime = LocalDateTime.parse(hour.getTime(), DATE_TIME_FORMATTER);

            weatherHour.setCity(dto.getLocation().getName());
            System.out.println(LocalDateTime.now() + "  INFO: Service Weather getWeatherNow, " + weatherHour.getCity());
            weatherHour.setTime(localDateTime.toLocalTime());
            weatherHour.setDate(localDateTime.toLocalDate());
            System.out.println(LocalDateTime.now() + "  INFO: Service Weather getWeatherNow, " + weatherHour.getDate() + " " + weatherHour.getTime());
            weatherHour.setText(hour.getCondition().getText());
            weatherHour.setIcon(hour.getCondition().getIcon());
            weatherHour.setCode(hour.getCondition().getCode());
            System.out.println(LocalDateTime.now() + "  INFO: Service Weather getWeatherNow, " + weatherHour.getText() + " " + weatherHour.getIcon() + " " + weatherHour.getCode());
            weatherHour.setTemp(hour.getTemp_c());
            weatherHour.setFeelLike(hour.getFeelslike_c());
            System.out.println(LocalDateTime.now() + "  INFO: Service Weather getWeatherNow, " + weatherHour.getTemp() + " " + weatherHour.getFeelLike());
            weatherHour.setSpeed(hour.getWind_kph());
            weatherHour.setDir(hour.getWind_dir());
            System.out.println(LocalDateTime.now() + "  INFO: Service Weather getWeatherNow, " + weatherHour.getSpeed() + " " + weatherHour.getDir());

            list.add(weatherHour);
        }
        return CompletableFuture.completedFuture(list);
    }
    @Async("weatherExecutor")
    @Override
    public CompletableFuture<List<WeatherForecast>> getListWeatherForecast(WeatherApiResponse dto) {

        System.out.println(LocalDateTime.now() + "  INFO: Service Weather getListWeatherForecast, " + dto.getLocation().getName());

        List<WeatherForecast> weathersList = new ArrayList<>();
        int i = 0;
        for (WeatherApiResponse.ForecastDay forecastDay: dto.getForecast().getForecastday()){
            i ++;
            System.out.println(LocalDateTime.now() + "  INFO: Day " + i + ", " + dto.getLocation().getName());
            WeatherForecast weathers = new WeatherForecast();

            weathers.setCity(dto.getLocation().getName());

            LocalDate localDate = LocalDate.parse(forecastDay.getDate());

            weathers.setDate(localDate);
            System.out.println(LocalDateTime.now() + "  INFO: Service Weather getWeatherNow, " + weathers.getDate());
            weathers.setAvgTemp(forecastDay.getDay().getAvgtemp_c());
            weathers.setMaxWind(forecastDay.getDay().getMaxwind_kph());
            System.out.println(LocalDateTime.now() + "  INFO: Service Weather getWeatherNow, " + weathers.getAvgTemp() + " " + weathers.getMaxWind());
            weathers.setText(forecastDay.getDay().getCondition().getText());
            weathers.setIcon(forecastDay.getDay().getCondition().getIcon());
            weathers.setCode(forecastDay.getDay().getCondition().getCode());
            System.out.println(LocalDateTime.now() + "  INFO: Service Weather getWeatherNow, " + weathers.getText() + " " + weathers.getIcon() + " " + weathers.getCode());
            weathersList.add(weathers);
        }
        return CompletableFuture.completedFuture(weathersList);
    }


}