package io.github.Surft14.weatherserver.repository;


import io.github.Surft14.weatherserver.model.WeatherNow;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class WeatherNowDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Optional<WeatherNow> getWeatherNow(String city){
        String jpql = "SELECT w FROM WeatherNow w WHERE w.city = :city";
        List<WeatherNow> result = entityManager
                .createQuery(jpql, WeatherNow.class)
                .setParameter("city", city)
                .getResultList();

        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    @Transactional
    public List<WeatherNow> findByCityTime(String city, LocalDateTime dateTime){
        String jpql = "SELECT w FROM WeatherNow w WHERE w.city = :city AND w.dateTime = :dateTime";
        List<WeatherNow> result = entityManager
                .createQuery(jpql, WeatherNow.class)
                .setParameter("city", city)
                .setParameter("dateTime", dateTime)
                .getResultList();
        return result;
    }

    @Transactional
    public WeatherNow saveWeatherNow(WeatherNow weather ){
        entityManager.persist(weather);
        return weather;
    }

    @Transactional
    public WeatherNow updateWeatherNow(WeatherNow weather){
        entityManager.merge(weather);
        return weather;
    }

    @Transactional
    public void deleteWeatherNow(WeatherNow weather){
        WeatherNow manage = entityManager.contains(weather) ? weather : entityManager.merge(weather);
        entityManager.remove(weather);
    }
}
