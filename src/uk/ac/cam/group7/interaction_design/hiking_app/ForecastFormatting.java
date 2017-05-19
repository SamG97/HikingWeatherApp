package uk.ac.cam.group7.interaction_design.hiking_app;

import org.bitpipeline.lib.owm.StatusWeatherData;

import java.time.DayOfWeek;
import java.util.*;

/**
 * Some useful functions to format forecast data into a useful format
 *
 * @author Sam Gooch
 */
public class ForecastFormatting {

    static Map<DayOfWeek, String> dayMap = new HashMap<>();

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

    public static int normaliseTemperature(float temperature) {
        return Math.round(temperature - 273.15f);
    }

    public static double convertWindSpeed(float windSpeed) {
        return Math.round(windSpeed * 10 / 0.44704f) / 10.0;
    }

    public static String getTimeLabel(long timeStamp) {
        return ((timeStamp % 86400) / 3600) + ":00";
    }

    public static DayOfWeek getToady() {
        Calendar calendar = Calendar.getInstance();
        int today = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (today == 0) {
            today = 7;
        }
        return DayOfWeek.of(today);
    }

    public static String dayAlias(DayOfWeek day) {
        return dayMap.get(day);
    }

}
