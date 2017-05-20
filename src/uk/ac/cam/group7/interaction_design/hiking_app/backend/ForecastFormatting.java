package uk.ac.cam.group7.interaction_design.hiking_app.backend;

import org.bitpipeline.lib.owm.StatusWeatherData;

import java.time.DayOfWeek;
import java.util.*;

/**
 * Some useful functions to format forecast data into a useful formats
 *
 * @author Sam Gooch
 */
public class ForecastFormatting {

    private static Map<DayOfWeek, String> dayMap = new HashMap<>();

    static {
        dayMap.put(DayOfWeek.MONDAY, "Mon");
        dayMap.put(DayOfWeek.TUESDAY, "Tues");
        dayMap.put(DayOfWeek.WEDNESDAY, "Wed");
        dayMap.put(DayOfWeek.THURSDAY, "Thur");
        dayMap.put(DayOfWeek.FRIDAY, "Fri");
        dayMap.put(DayOfWeek.SATURDAY, "Sat");
        dayMap.put(DayOfWeek.SUNDAY, "Sun");
    }

    /**
     * Sorts the forecast into daily forecasts
     *
     * @param allForecasts The full forecast
     * @return Sorted daily forecast
     */
    public static Map<DayOfWeek, List<StatusWeatherData>> getDailyForecasts(List<StatusWeatherData> allForecasts) {
        Map<DayOfWeek, List<StatusWeatherData>> dailyForecasts = new HashMap<>();

        long currentDay = (System.currentTimeMillis() / 1000) / 86400;
        int today = getToady().getValue();
        for (StatusWeatherData forecast : allForecasts) {
            if (forecast.getDateTime() - (System.currentTimeMillis() / 1000) < -10800) {
                continue;
            }
            long forecastDay = forecast.getDateTime() / 86400;
            int forecastDayValue = today;
            while (forecastDay > currentDay) {
                forecastDayValue++;
                forecastDay--;
            }
            while (forecastDayValue > 7) {
                forecastDayValue -= 7;
            }
            DayOfWeek dayValue = DayOfWeek.of(forecastDayValue);
            if (dailyForecasts.containsKey(dayValue)) {
                dailyForecasts.get(dayValue).add(forecast);
            } else {
                List<StatusWeatherData> forecastList = new LinkedList<>();
                forecastList.add(forecast);
                dailyForecasts.put(dayValue, forecastList);
            }
        }
        return dailyForecasts;
    }

    /**
     * Converts the temperature from its stored value in Kelvin to Celsius and rounds
     *
     * @param temperature Raw temperature data from JSON response
     * @return Normalised temperature
     */
    public static int normaliseTemperature(float temperature) {
        return Math.round(temperature - 273.15f);
    }

    /**
     * Converts wind speed from m/s to mph
     *
     * @param windSpeed Raw wind speed data from JSON response
     * @return Wind speed in mph
     */
    public static double convertWindSpeed(float windSpeed) {
        return Math.round(windSpeed * 10 / 0.44704f) / 10.0;
    }

    /**
     * Get time label (e.g. 12:00) from a timestamp
     *
     * @param timeStamp Timestamp for the required label
     * @return Label to display
     */
    public static String getTimeLabel(long timeStamp) {
        String label = ((timeStamp % 86400) / 3600) + ":00";
        if (label.length() < 5) {
            label = "0" + label;
        }
        return label;
    }

    /**
     * Get today's day of the week
     *
     * @return Today as DayOfWeek
     */
    public static DayOfWeek getToady() {
        Calendar calendar = Calendar.getInstance();
        int today = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (today == 0) {
            today = 7;
        }
        return DayOfWeek.of(today);
    }

    /**
     * Converts DayOfWeek to shorthand name for day
     *
     * @param day Day to convert
     * @return Shortened name
     */
    public static String dayAlias(DayOfWeek day) {
        return dayMap.get(day);
    }

}
