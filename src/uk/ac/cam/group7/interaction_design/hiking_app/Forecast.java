package uk.ac.cam.group7.interaction_design.hiking_app;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Holds forecast data, is independent of source (i.e. API call or file import)
 * @author Sam Gooch
 */
public class Forecast {

    private final List<ForecastDay> dataStore;
    private final LocalDateTime updateTime;
    private LocalDateTime lastUpdateAttempt;

    /**
     * Constructor for HistoricForecast, will load the data stored in the location accessed by the filename
     * @param forecasts A list of daily forecasts that make up the forecast for a location
     * @param timeStamp The time that the forecast was last updated in the LocalDateTime format
     */
    public Forecast(List<ForecastDay> forecasts, LocalDateTime timeStamp) {
        this.dataStore = forecasts;
        this.updateTime = timeStamp;
        this.lastUpdateAttempt = LocalDateTime.now();
    }

    /**
     * Getter for the list of daily forecasts
     * @return dataStore The list of daily forecasts
     */
    public List<ForecastDay> getDataStore() {
        return dataStore;
    }

    /**
     * Getter for update time
     * @return updateTime The time the forecasts were last updated
     */
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    /**
     * Getter for checking for last update time
     * @return lastUpdateAttempt Timestamp for last time an update was attempted
     */
    public LocalDateTime getLastUpdateAttempt() {
        return lastUpdateAttempt;
    }

    /**
     * Marks last update attempt
     * @param lastUpdateAttempt Last time that an attempt was made to update the forecast
     */
    public void setLastUpdateAttempt(LocalDateTime lastUpdateAttempt) {
        this.lastUpdateAttempt = lastUpdateAttempt;
    }

}
