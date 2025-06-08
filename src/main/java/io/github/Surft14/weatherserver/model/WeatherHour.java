package io.github.Surft14.weatherserver.model;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "weather_hour")
public class WeatherHour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime time;

    private Double temp;
    private Double feelLike;

    private String icon;
    private String text;
    private Long code;

    private Double speed;
    private String dir;

}
