ALTER TABLE weather_now
    ADD COLUMN temp_f DOUBLE,
    ADD COLUMN wind_mph DOUBLE,
    ADD COLUMN feelslike_f DOUBLE,
    ADD COLUMN date DATE,
    ADD COLUMN time TIME;

ALTER TABLE weather_hour
    ADD COLUMN temp_f DOUBLE,
    ADD COLUMN wind_mph DOUBLE,
    ADD COLUMN feelslike_f DOUBLE;

ALTER TABLE weathers
    ADD COLUMN avgtemp_f DOUBLE,
    ADD COLUMN maxwind_mph DOUBLE;


UPDATE weather_now
    temp_f = ROUND((temp * 9/5) + 32, 1),
    wind_mph = ROUND(speed / 1.609, 1),
    feelslike_f = ROUND((feel_like * 9/5) + 32, 1),
    date = DATE(date_time),
    time = TIME(date_time);

UPDATE weather_hour
    temp_f = ROUND((temp * 9/5) + 32, 1),
    wind_mph = ROUND(speed / 1.609, 1),
    feelslike_f = ROUND((feel_like * 9/5) + 32, 1);

UPDATE weathers
    avgtemp_f = ROUND((avg_temp * 9/5) + 32, 1),
    maxwind_mph = ROUND(max_wind / 1.609, 1),

ALTER TABLE weather_now
  MODIFY COLUMN temp_f    DOUBLE NOT NULL,
  MODIFY COLUMN feelslike_f DOUBLE NOT NULL,
  MODIFY COLUMN wind_mph DOUBLE NOT NULL,
  MODIFY COLUMN date     DATE   NOT NULL,
  MODIFY COLUMN time     TIME   NOT NULL;

ALTER TABLE weather_hour
 MODIFY COLUMN temp_f    DOUBLE NOT NULL,
 MODIFY COLUMN feelslike_f DOUBLE NOT NULL,
 MODIFY COLUMN wind_mph DOUBLE NOT NULL;

ALTER weathers
 MODIFY COLUMN avgtemp_f    DOUBLE NOT NULL,
 MODIFY COLUMN maxwind_mph DOUBLE NOT NULL;