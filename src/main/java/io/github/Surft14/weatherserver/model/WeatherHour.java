package io.github.Surft14.weatherserver.model;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "weather_hour")
public class WeatherHour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private LocalTime time;
    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private Double temp;
    @Column(nullable = false)
    private Double tempF;
    @Column(nullable = false)
    private Double feelLike;
    @Column(nullable = false)
    private Double feelLikeF;

    @Column(nullable = false)
    private String icon;
    @Column(nullable = false)
    private String text;
    @Column(nullable = false)
    private Long code;

    @Column(nullable = false)
    private Double speed;
    @Column(nullable = false)
    private Double speedM;
    @Column(nullable = false)
    private String dir;

}
