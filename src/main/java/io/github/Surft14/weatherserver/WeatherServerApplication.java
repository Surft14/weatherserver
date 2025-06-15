package io.github.Surft14.weatherserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDateTime;

@SpringBootApplication
@EnableScheduling
public class WeatherServerApplication {

	public static void main(String[] args) {
		try{
			SpringApplication.run(WeatherServerApplication.class, args);
		} catch (Exception e) {
			System.out.println(LocalDateTime.now() + "  ERROR: " + e.getMessage());
		}
	}

}
