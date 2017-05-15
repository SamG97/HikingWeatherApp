package uk.ac.cam.group7.interaction_design.hiking_app;

import java.time.LocalTime;
import java.util.ArrayList;

/**
 * Holds the forecast data for a location for a single day
 * @author Sam Gooch
 */
public class ForecastDay {

    private final ArrayList<ForecastElement> forecastStore;
    private final LocalTime sunrise;
    private final LocalTime sunset;
    private final double averageTemp;
    private final WeatherType averageType;

    public ForecastDay(ArrayList<ForecastElement> forecasts, int sunriseHour, int sunriseMinute, int sunsetHour,
                       int sunsetMinute, double averageTemp, WeatherType averageType) {
        this.forecastStore = forecasts;
        this.sunrise = LocalTime.of(sunriseHour, sunriseMinute);
        this.sunset = LocalTime.of(sunsetHour, sunsetMinute);
        this.averageTemp = averageTemp;
        this.averageType = averageType;
    }

    public ArrayList<ForecastElement> getForecastStore() {
        return forecastStore;
    }

    public LocalTime getSunrise() {
        return sunrise;
    }

    public LocalTime getSunset() {
        return sunset;
    }

    public double getAverageTemp() {
        return averageTemp;
    }

    public WeatherType getAverageType() {
        return averageType;
    }

    public boolean compareForecasts(ForecastDay comparison) {
        //TODO: Implement comparision between two forecasts
        return true;
    }

}
