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
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private Double avgTemp;
    @Column(nullable = false)
    private Double avgTempF;
    @Column(nullable = false)
    private Double maxWind;
    @Column(nullable = false)
    private Double maxWindM;

    @Column(nullable = false)
    private String text;
    @Column(nullable = false)
    private String icon;
    @Column(nullable = false)
    private Long code;

}
