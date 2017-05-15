package uk.ac.cam.group7.interaction_design.hiking_app;

import java.time.LocalDateTime;

/**
 * Holds an individual hour's forecast data
 * @author Sam Gooch
 */
public class ForecastElement {

    private final LocalDateTime forecastTime;
    private final double temperature;
    private final WeatherType type;
    private final double precipitation;
    private final Wind wind;
    private final double humidity;
    private final VisibilityType visibility;

    /**
     * Constructor for an individual hour's forecast
     * @param year Year of the forecast
     * @param month Month of the forecast
     * @param day Day of the forecast
     * @param hour Hour of the forecast
     * @param temperature Temperature for the hour (in C)
     * @param type Weather type for the hour
     * @param precipitation Expected precipitation levels for the hour (in mm)
     * @param windSpeed Wind speed for the hour (in mph)
     * @param windDirection Wind direction (bearing in degrees)
     * @param humidity Humidity levels for the hour (in %)
     * @param visibility Visibility levels for the hour
     */
    public ForecastElement(int year, int month, int day, int hour, double temperature, WeatherType type,
                           double precipitation, double windSpeed, double windDirection, double humidity,
                           VisibilityType visibility) {
        this.forecastTime = LocalDateTime.of(year, month , day, hour, 0);
        this.temperature = temperature;
        this.type = type;
        this.precipitation = precipitation;
        this.wind = new Wind(windSpeed, windDirection);
        this.humidity = humidity;
        this.visibility = visibility;
    }

    /**
     * Getter for the time for the forecast
     * @return forecastTime Timestamp for the hour that the forecast is for
     */
    public LocalDateTime getForecastTime() {
        return forecastTime;
    }

    /**
     * Getter for the temperature
     * @return temperature The temperature for the hour (in C)
     */
    public double getTemperature() {
        return temperature;
    }

    /**
     * Getter for weather type
     * @return type The weather type for the hour
     */
    public WeatherType getType() {
        return type;
    }

    /**
     * Getter for precipitation levels
     * @return precipitation The expected level of precipitation for the hour (in mm)
     */
    public double getPrecipitation() {
        return precipitation;
    }

    /**
     * Getter for the wind data
     * @return wind The wind speed (in mph) and direction (bearing in degrees) in a single object for the hour
     */
    public Wind getWind() {
        return wind;
    }

    /**
     * Getter for the humidity
     * @return humidity The humidity for the hour (in %)
     */
    public double getHumidity() {
        return humidity;
    }

    /**
     * Getter for the visibility
     * @return visibility The visibility for the hour
     */
    public VisibilityType getVisibility() {
        return visibility;
    }

    /**
     * Compares two individual hourly forecasts
     * @param comparison The hourly forecast to compare this one to
     * @return DifferentType Encoding of differing feature; returns NONE if no difference
     */
    public DifferentType compareForecasts(ForecastElement comparison) {
        //TODO: Implement comparision between two forecasts
        return DifferentType.NONE;
    }

}
