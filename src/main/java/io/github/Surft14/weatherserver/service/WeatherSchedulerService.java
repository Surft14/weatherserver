package io.github.Surft14.weatherserver.service;


import io.github.Surft14.weatherserver.model.WeatherNow;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WeatherSchedulerService {

    private final WeatherService service;

    private final String city = "Cheboksary";
    private final String apiKey = "e484c70c78e84b779ab151237251002";

    @Scheduled(fixedRate = 30 * 60 * 1000)
    public void fetchWeatherPeriodically(){
        try {
            WeatherNow weather = service.getWeatherNow(city, apiKey);
            service.saveWeatherNow(weather);
            System.out.println("Weather update: " + weather.getCity() + " " + weather.getTemp());
        } catch (Exception e) {
            System.err.println("Error at update weather: " + e.getMessage());
        }
    }

}
