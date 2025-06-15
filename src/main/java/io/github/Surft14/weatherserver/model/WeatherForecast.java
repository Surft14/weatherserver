package io.github.Surft14.weatherserver.model;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "weathers")
public class WeatherForecast {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;

    private LocalDate date;

    private double avgTemp;
    private double maxWind;

    private String text;
    private String icon;
    private Long code;

}
