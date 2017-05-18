package uk.ac.cam.group7.interaction_design.hiking_app;

import org.bitpipeline.lib.owm.StatusWeatherData;

import java.time.DayOfWeek;
import java.util.*;

/**
 * Created by Dávid on 2017.05.18..
 *
 * Passing along a location will sort the associated weather data into daily buckets
 */
public class FiveDayForecast {

    Map<DayOfWeek,List<StatusWeatherData>> days = new LinkedHashMap<>(10);

    public FiveDayForecast( List<StatusWeatherData>  weather){
        Calendar cal = Calendar.getInstance();
        int day = cal.DAY_OF_WEEK;
        int hour = cal.HOUR_OF_DAY;
        int timeTillTomorrow = (24- hour) * 3600;
        for(StatusWeatherData w : weather){
            LinkedList<StatusWeatherData> temp = new LinkedList<StatusWeatherData>();
            if(w.getDateTime() - System.currentTimeMillis() / 1000 < timeTillTomorrow){
                temp.add(w);
            } else {
                days.put( DayOfWeek.of(day) , temp );
                temp = new LinkedList<StatusWeatherData>();
                temp.add(w);
                day = 7 % (day+1);
                timeTillTomorrow += 24*3600;
            }
        }
        DayOfWeek today = DayOfWeek.of(Calendar.DAY_OF_WEEK);
        final long cMinutes = System.currentTimeMillis() / 1000;
    }

    public Map<DayOfWeek, List<StatusWeatherData>> getDays() {
        return days;
    }
}
