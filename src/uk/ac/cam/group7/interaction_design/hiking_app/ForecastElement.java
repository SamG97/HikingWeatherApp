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

    public LocalDateTime getForecastTime() {
        return forecastTime;
    }

    public double getTemperature() {
        return temperature;
    }

    public WeatherType getType() {
        return type;
    }

    public double getPrecipitation() {
        return precipitation;
    }

    public Wind getWind() {
        return wind;
    }

    public double getHumidity() {
        return humidity;
    }

    public VisibilityType getVisibility() {
        return visibility;
    }

    public boolean compareForecasts(ForecastElement comparison) {
        //TODO: Implement comparision between two forecasts
        return true;
    }

}
