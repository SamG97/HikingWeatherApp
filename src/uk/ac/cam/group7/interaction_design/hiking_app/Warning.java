package uk.ac.cam.group7.interaction_design.hiking_app;

import org.bitpipeline.lib.owm.WeatherData;

public class Warning {

    private final WeatherData.WeatherCondition.ConditionCode type;
    private final int severity;

    public Warning(WeatherData.WeatherCondition.ConditionCode type, int severity) {
        this.type = type;
        this.severity = severity;
    }

    public WeatherData.WeatherCondition.ConditionCode getWarningType() {
        return type;
    }

    public int getSeverity() {
        return severity;
    }

    public String getWarningMessage() {
        switch (severity) {
            case 0: return "The forecast has changed." + type.toString() + " weather is now expected";
            case 1: return "Green weather warning of " + type.toString();
            case 2: return "Amber weather warning of " + type.toString();
            case 3: return "Red weather warning of " + type.toString();
            default: return "Unknown weather warning";
        }

    }

}
