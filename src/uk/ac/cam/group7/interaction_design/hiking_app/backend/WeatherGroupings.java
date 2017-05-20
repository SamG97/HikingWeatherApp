package uk.ac.cam.group7.interaction_design.hiking_app.backend;

import org.bitpipeline.lib.owm.WeatherData;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class WeatherGroupings {

    /**
     * Groups weather conditions into similar groups of weather as found by card sorting
     *
     * @return The grouped weather conditions
     */
    static List<List<WeatherData.WeatherCondition.ConditionCode>> getGroups() {
        List<List<WeatherData.WeatherCondition.ConditionCode>> weatherGroupings = new LinkedList<>();

        List<WeatherData.WeatherCondition.ConditionCode> sunny = new LinkedList<>();
        List<WeatherData.WeatherCondition.ConditionCode> rain = new LinkedList<>();
        List<WeatherData.WeatherCondition.ConditionCode> severe = new LinkedList<>();
        List<WeatherData.WeatherCondition.ConditionCode> overcast = new LinkedList<>();
        List<WeatherData.WeatherCondition.ConditionCode> snow = new LinkedList<>();
        List<WeatherData.WeatherCondition.ConditionCode> other = new LinkedList<>();

        severe.add(WeatherData.WeatherCondition.ConditionCode.THUNDERSTORM);
        severe.add(WeatherData.WeatherCondition.ConditionCode.THUNDERSTORM_WITH_DRIZZLE);
        severe.add(WeatherData.WeatherCondition.ConditionCode.THUNDERSTORM_WITH_HEAVY_DRIZZLE);
        severe.add(WeatherData.WeatherCondition.ConditionCode.THUNDERSTORM_WITH_HEAVY_RAIN);
        severe.add(WeatherData.WeatherCondition.ConditionCode.THUNDERSTORM_WITH_LIGHT_DRIZZLE);
        severe.add(WeatherData.WeatherCondition.ConditionCode.THUNDERSTORM_WITH_LIGHT_RAIN);
        severe.add(WeatherData.WeatherCondition.ConditionCode.THUNDERSTORM_WITH_RAIN);
        severe.add(WeatherData.WeatherCondition.ConditionCode.LIGHT_THUNDERSTORM);
        severe.add(WeatherData.WeatherCondition.ConditionCode.HEAVY_THUNDERSTORM);
        severe.add(WeatherData.WeatherCondition.ConditionCode.RAGGED_THUNDERSTORM);
        severe.add(WeatherData.WeatherCondition.ConditionCode.HAIL);

        rain.add(WeatherData.WeatherCondition.ConditionCode.HEAVY_INTENSITY_RAIN);
        rain.add(WeatherData.WeatherCondition.ConditionCode.LIGHT_INTENSITY_DRIZZLE);
        rain.add(WeatherData.WeatherCondition.ConditionCode.DRIZZLE);
        rain.add(WeatherData.WeatherCondition.ConditionCode.HEAVY_INTENSITY_DRIZZLE);
        rain.add(WeatherData.WeatherCondition.ConditionCode.LIGHT_INTENSITY_DRIZZLE_RAIN);
        rain.add(WeatherData.WeatherCondition.ConditionCode.DRIZZLE_RAIN);
        rain.add(WeatherData.WeatherCondition.ConditionCode.HEAVY_INTENSITY_DRIZZLE_RAIN);
        rain.add(WeatherData.WeatherCondition.ConditionCode.SHOWER_DRIZZLE);
        rain.add(WeatherData.WeatherCondition.ConditionCode.LIGHT_RAIN);
        rain.add(WeatherData.WeatherCondition.ConditionCode.MODERATE_RAIN);
        rain.add(WeatherData.WeatherCondition.ConditionCode.EXTREME_RAIN);
        rain.add(WeatherData.WeatherCondition.ConditionCode.FREEZING_RAIN);
        rain.add(WeatherData.WeatherCondition.ConditionCode.LIGHT_INTENSITY_SHOWER_RAIN);
        rain.add(WeatherData.WeatherCondition.ConditionCode.SHOWER_RAIN);
        rain.add(WeatherData.WeatherCondition.ConditionCode.HEAVY_INTENSITY_SHOWER_RAIN);

        snow.add(WeatherData.WeatherCondition.ConditionCode.SNOW);
        snow.add(WeatherData.WeatherCondition.ConditionCode.LIGHT_SNOW);
        snow.add(WeatherData.WeatherCondition.ConditionCode.HEAVY_SNOW);
        snow.add(WeatherData.WeatherCondition.ConditionCode.SLEET);
        snow.add(WeatherData.WeatherCondition.ConditionCode.SHOWER_SNOW);

        overcast.add(WeatherData.WeatherCondition.ConditionCode.FOG);
        overcast.add(WeatherData.WeatherCondition.ConditionCode.MIST);
        overcast.add(WeatherData.WeatherCondition.ConditionCode.SMOKE);
        overcast.add(WeatherData.WeatherCondition.ConditionCode.HAZE);
        overcast.add(WeatherData.WeatherCondition.ConditionCode.SAND_OR_DUST_WHIRLS);
        overcast.add(WeatherData.WeatherCondition.ConditionCode.OVERCAST_CLOUDS);

        sunny.add(WeatherData.WeatherCondition.ConditionCode.SKY_IS_CLEAR);
        sunny.add(WeatherData.WeatherCondition.ConditionCode.FEW_CLOUDS);
        sunny.add(WeatherData.WeatherCondition.ConditionCode.SCATTERED_CLOUDS);
        sunny.add(WeatherData.WeatherCondition.ConditionCode.BROKEN_CLOUDS);

        other.add(WeatherData.WeatherCondition.ConditionCode.UNKNOWN);
        other.add(WeatherData.WeatherCondition.ConditionCode.TORNADO);
        other.add(WeatherData.WeatherCondition.ConditionCode.TROPICAL_STORM);
        other.add(WeatherData.WeatherCondition.ConditionCode.HURRICANE);
        other.add(WeatherData.WeatherCondition.ConditionCode.COLD);
        other.add(WeatherData.WeatherCondition.ConditionCode.HOT);
        other.add(WeatherData.WeatherCondition.ConditionCode.WINDY);

        weatherGroupings.add(sunny);
        weatherGroupings.add(rain);
        weatherGroupings.add(severe);
        weatherGroupings.add(overcast);
        weatherGroupings.add(snow);
        weatherGroupings.add(other);

        return weatherGroupings;
    }

    /**
     * Maps the severity rating of a weather warning to an appropriate name
     *
     * @return String descriptor of the severity
     */
    static Map<Integer, String> getSeverity() {
        Map<Integer, String> severityDescriptor = new HashMap<>();
        severityDescriptor.put(1, "Yellow");
        severityDescriptor.put(2, "Amber");
        severityDescriptor.put(3, "Red");
        return severityDescriptor;
    }

}
