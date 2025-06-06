package io.github.Surft14.weatherserver.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "city_table")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;
}
