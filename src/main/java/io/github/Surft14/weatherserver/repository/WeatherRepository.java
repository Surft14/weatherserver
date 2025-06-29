package io.github.Surft14.weatherserver.repository;

import io.github.Surft14.weatherserver.model.WeatherNow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface WeatherRepository extends JpaRepository<WeatherNow, Long> {
    List<WeatherNow> findByCity(String city);

    List<WeatherNow> findByCityAndDateTime(String city, LocalDateTime dateTime);

    WeatherNow findTopByCityOrderByDateTimeDesc(@Param("city") String city);

    List<WeatherNow> findByDate(LocalDate date);

    @Query("SELECT w FROM WeatherNow w WHERE w.city = :city AND w.date = :date")
    List<WeatherNow> findByCityAndDate(@Param("city") String city, @Param("date") LocalDate date);

    @Query("SELECT w FROM WeatherNow w WHERE w.city = :city AND w.date >= :date")
    List<WeatherNow> findByCityAndDateAfter(@Param("city") String city, @Param("date") LocalDate date);

    @Query("SELECT w FROM WeatherNow w WHERE w.city = :city AND w.date <= :date")
    List<WeatherNow> findByCityAndDateBefore(@Param("city") String city, @Param("date") LocalDate date);

    @Query("SELECT w FROM WeatherNow w WHERE w.city = :city AND w.date BETWEEN :start AND :end")
    List<WeatherNow> findByCityAndDateBetween(@Param("city") String city, @Param("start") LocalDate start, @Param("end") LocalDate end);
}
