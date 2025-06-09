package io.github.Surft14.weatherserver.model;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "weather_now")
public class WeatherNow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;
    private String region;
    private String country;
    private LocalDateTime dateTime;
    private LocalDateTime lastUpdateTime;

    private Double temp;
    private Double feelLike;
    private String dir;
    private Double speed;

    private String text;
    private String icon;
    private Long code;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "weather_now_id", referencedColumnName = "id")
    private List<WeatherHour> listHour = new ArrayList<>();

}
