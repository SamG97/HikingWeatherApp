package uk.ac.cam.group7.interaction_design.hiking_app;

import java.util.ArrayList;

public class ForecastDay {

    private final ArrayList<ForecastElement> forecastStore;
    private final TimeStamp sunrise;
    private final TimeStamp sunset;
    private final double averageTemp;
    private final WeatherType averageType;

    public ForecastDay(ArrayList<ForecastElement> forecasts, int sunriseHour, int sunriseMinute, int sunsetHour,
                       int sunsetMinute, double averageTemp, WeatherType averageType) {
        this.forecastStore = forecasts;
        this.sunrise = new TimeStamp(sunriseHour, sunriseMinute);
        this.sunset = new TimeStamp(sunsetHour, sunsetMinute);
        this.averageTemp = averageTemp;
        this.averageType = averageType;
    }

    public ArrayList<ForecastElement> getForecastStore() {
        return forecastStore;
    }

    public TimeStamp getSunrise() {
        return sunrise;
    }

    public TimeStamp getSunset() {
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
