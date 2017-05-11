package uk.ac.cam.group7.interaction_design.hiking_app;

import java.util.HashMap;
import java.util.Map;

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

    private WeatherType(char name) {
        this.name = name;
    }

    public String toString() {
        return String.valueOf(name);
    }

    public static WeatherType valueOf(char name) {
        return types.get(name);
    }
}
