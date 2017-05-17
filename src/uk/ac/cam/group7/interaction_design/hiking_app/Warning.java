package uk.ac.cam.group7.interaction_design.hiking_app;

import org.bitpipeline.lib.owm.WeatherData;

public class Warning {

    private final WeatherData.WeatherCondition.ConditionCode type;
    private final String description;

    public Warning(WeatherData.WeatherCondition.ConditionCode type, String description) {
        this.type = type;
        this.description = description;
    }

    public WeatherData.WeatherCondition.ConditionCode getWarningType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

}
