package uk.ac.cam.group7.interaction_design.hiking_app;

import java.util.HashMap;
import java.util.Map;

/**
 * Stores an encoding for the different types of weather
 * @author Sam Gooch
 */
public enum WeatherType {
    SUNNY('S'), PARTIAL_CLOUD('P'), DARK_CLOUD('D'), RAIN_SHOWERS('R'), LIGHT_RAIN('L'), HEAVY_RAIN('H'),
    THUNDERSTORMS('T'), LIGHT_SNOW('S'), HEAVY_SNOW('B'), HAIL('I'), FOG('F');

    private static Map<Character, WeatherType> types = new HashMap<>();
    static {
        for (WeatherType type : WeatherType.values()) {
            types.put(type.name, type);
        }
    }

    private char name;

    /**
     * Private constructor for WeatherType
     * @param name The value to be encoded to a VisibilityType
     */
    private WeatherType(char name) {
        this.name = name;
    }

    /**
     * Converts WeatherType to a String
     * @return String Value of VisibilityType
     */
    public String toString() {
        return String.valueOf(name);
    }

    /**
     * Finds value as WeatherType of an un-encoded input
     * @param name The un-encoded value
     * @return WeatherType The encoded value of name
     */
    public static WeatherType valueOf(char name) {
        return types.get(name);
    }

}
