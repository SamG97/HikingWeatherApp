package uk.ac.cam.group7.interaction_design.hiking_app;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * Holds the forecast data for a location for a single day
 * @author Sam Gooch
 */
public class ForecastDay {

    private final LocalDateTime day;
    private final List<ForecastElement> forecastStore;
    private final LocalTime sunrise;
    private final LocalTime sunset;
    private final double averageTemp;
    private final WeatherType averageType;

    /**
     * Constructor for ForecastDay
     * @param date The date that the forecast is for
     * @param forecasts A list of all hourly forecasts for the day
     * @param sunriseHour The hour of the sunrise for the day
     * @param sunriseMinute The minute of the sunrise for the day
     * @param sunsetHour The hour of the sunset for the day
     * @param sunsetMinute The minute of the sunset for the day
     * @param averageTemp The average temperature of the day to display in forecast summaries
     * @param averageType The average weather type of the day to display in forecast summaries
     */
    public ForecastDay(LocalDateTime date, List<ForecastElement> forecasts, int sunriseHour, int sunriseMinute,
                       int sunsetHour, int sunsetMinute, double averageTemp, WeatherType averageType) {
        this.day = date;
        this.forecastStore = forecasts;
        this.sunrise = LocalTime.of(sunriseHour, sunriseMinute);
        this.sunset = LocalTime.of(sunsetHour, sunsetMinute);
        this.averageTemp = averageTemp;
        this.averageType = averageType;
    }

    /**
     * Getter for the date of the forecast
     * @return day Date of the forecast
     */
    public LocalDateTime getDay() {
        return day;
    }

    /**
     * Getter for the list of hourly forecasts
     * @return forecastStore The list of forecasts
     */
    public List<ForecastElement> getForecastStore() {
        return forecastStore;
    }

    /**
     * Getter for the time of the sunrise
     * @return sunrise The sunrise time
     */
    public LocalTime getSunrise() {
        return sunrise;
    }

    /**
     * Getter for the time of the sunset
     * @return sunrise The sunset time
     */
    public LocalTime getSunset() {
        return sunset;
    }

    /**
     * Getter for average temperature
     * @return averageTemp The average temperature for the day
     */
    public double getAverageTemp() {
        return averageTemp;
    }

    /**
     * Getter for average weather type
     * @return averageType The average type of weather for the day
     */
    public WeatherType getAverageType() {
        return averageType;
    }

    /**
     * Compares two forecasts to see if they are sufficiently similar
     * @param comparison The forecast to compare this one to
     * @return DifferentType Encoding of differing feature; returns NONE if no difference
     */
    public DifferentType compareDailyForecasts(ForecastDay comparison) {
        //TODO: Implement comparision between two forecasts
        return DifferentType.NONE;
    }

}
