package uk.ac.cam.group7.interaction_design.hiking_app;

import org.bitpipeline.lib.owm.WeatherData;

/**
 * Stores a single weather warning
 *
 * @author Sam Gooch
 */
public class Warning {

    private final WeatherData.WeatherCondition.ConditionCode type;
    private final String description;

    /**
     * Constructor for a new warning
     *
     * @param type        The type of weather the warning concerns
     * @param description A description of the weather warning
     */
    public Warning(WeatherData.WeatherCondition.ConditionCode type, String description) {
        this.type = type;
        this.description = description;
    }

    /**
     * Getter for the type of weather warning
     *
     * @return The weather condition that the warning concerns
     */
    public WeatherData.WeatherCondition.ConditionCode getWarningType() {
        return type;
    }

    /**
     * Getter for the description of a weather warning
     *
     * @return The description of the warning
     */
    public String getDescription() {
        return description;
    }

}
