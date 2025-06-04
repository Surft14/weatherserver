package io.github.Surft14.weatherserver.model;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "weather_now")
public class WeatherNow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;
    private LocalDateTime dateTime;

    private Double temp;
    private Double feelLike;
    private String dir;
    private Double speed;

    private String weather_text;
    private String weather_url;
    private Long weather_code;
}
