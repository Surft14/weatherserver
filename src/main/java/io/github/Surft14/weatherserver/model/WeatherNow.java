package io.github.Surft14.weatherserver.model;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "weather_now")
public class WeatherNow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String region;
    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private LocalDateTime dateTime;
    @Column(nullable = false)
    private LocalDate date;
    @Column(nullable = false)
    private LocalTime time;
    @Column(nullable = false)
    private LocalDateTime lastUpdateTime;

    @Column(nullable = false)
    private Double temp;
    @Column(nullable = false)
    private Double feelLike;

    @Column(nullable = false)
    private Double tempF;
    @Column(nullable = false)
    private Double feelLikeF;

    @Column(nullable = false)
    private String dir;
    @Column(nullable = false)
    private Double speed;
    @Column(nullable = false)
    private Double speedM;

    @Column(nullable = false)
    private String text;
    @Column(nullable = false)
    private String icon;
    @Column(nullable = false)
    private Long code;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "weather_now_id", referencedColumnName = "id")
    private List<WeatherHour> listHour = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "weather_now_id", referencedColumnName = "id")
    private List<WeatherForecast> weathersList = new ArrayList<>();

}
