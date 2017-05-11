package uk.ac.cam.group7.interaction_design.hiking_app;

import java.util.List;

/**
 * Holds forecast data, is independent of source (i.e. API call or file import)
 * @author Sam Gooch
 */
public class Forecast {

    private List<ForecastDay> dataStore;

    public Forecast(List<ForecastDay> forecasts) {
        this.dataStore = forecasts;
    }

}
