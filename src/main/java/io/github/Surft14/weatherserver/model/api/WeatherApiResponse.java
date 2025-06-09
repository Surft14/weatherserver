package io.github.Surft14.weatherserver.model.api;

import lombok.Data;

import java.util.List;

@Data
public class WeatherApiResponse {
    private Location location;
    private Current current;
    private Forecast forecast;

    @Data
    public static class Location {
        private String name;
        private String region;
        private String country;
        private String tz_id;
        private String localtime;
    }

    @Data
    public static class Current {
        private String last_updated;
        private double temp_c;
        private Condition condition;
        private double wind_kph;
        private String wind_dir;
        private double feelslike_c;
    }

    @Data
    public static class Condition {
        private String text;
        private String icon;
        private long code;
    }

    @Data
    public static class Forecast {
        private List<ForecastDay> forecastday;
    }

    @Data
    public static class ForecastDay {
        private String date;
        private Day day;
        private List<Hour> hour;
    }

    @Data
    public static class Day {
        private double avgtemp_c;
        private double maxwind_kph;
        private Condition condition;
    }

    @Data
    public static class Hour {
        private String time;
        private double temp_c;
        private Condition condition;
        private double wind_kph;
        private String wind_dir;
        private double feelslike_c;
    }
}