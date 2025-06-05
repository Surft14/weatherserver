package io.github.Surft14.weatherserver.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WeatherApiResponse {
    private Location location;
    private Current current;


    @Data
    public static class Location {
        private String name;
        private String region;
        private String country;
        // В API возвращается строка "2025-06-04 14:16"
        @JsonProperty("localtime")
        private String localtime;
    }

    @Data
    public static class Current {

        private Condition condition;

        @Data
        public static class Condition {
            @JsonProperty("text")
            private String text;

            @JsonProperty("icon")
            private String icon;

            @JsonProperty("code")
            private Long code;
        }

        @JsonProperty("last_updated")
        private String lastUpdate;

        @JsonProperty("temp_c")
        private Double tempC;

        @JsonProperty("feelslike_c")
        private Double feelslikeC;

        @JsonProperty("wind_kph")
        private Double windKph;

        @JsonProperty("wind_dir")
        private String windDir;

    }
}