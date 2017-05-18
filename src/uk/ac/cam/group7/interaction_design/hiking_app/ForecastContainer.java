package uk.ac.cam.group7.interaction_design.hiking_app;

import org.bitpipeline.lib.owm.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Stores all locations and forecasts in a single format and provides an interface to access this data in a helpful way
 * Singleton class to prevent multiple instantiation
 *
 * @author Sam Gooch
 */
public class ForecastContainer {

    private final static ForecastContainer reference = new ForecastContainer();

    private final static String pSep = "\\";
    private final static OwmClient api = new OwmClient();
    private final static List<List<WeatherData.WeatherCondition.ConditionCode>> weatherGroupings;
    private final static Map<Integer, String> severityDescriptor;

    private Map<Location, List<StatusWeatherData>> weatherDataMap;
    private List<Location> favouriteLocations;
    private List<Location> recentLocations;

    static {
        api.setAPPID("d12c4a04b7d0170dff8f1afca1e4c0ff");

        weatherGroupings = new LinkedList<>();
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

        severityDescriptor = new HashMap<>();
        severityDescriptor.put(1, "yellow");
        severityDescriptor.put(2, "amber");
        severityDescriptor.put(3, "red");
    }

    /**
     * Get a reference to the singleton ForecastContainer
     *
     * @return reference
     */
    public static ForecastContainer getReference() {
        return reference;
    }

    /**
     * Loads previously saved data and tries to contact the API for fresh data
     * Generates warnings for the old data if it has changed
     */
    private ForecastContainer() {
        weatherDataMap = new HashMap<>();
        favouriteLocations = importLocations(Paths.get("data" + pSep + "favourites.csv"));
        for (Location location : favouriteLocations) {
            List<StatusWeatherData> historicData = JsonIO.readJson(location.getPath());
            List<StatusWeatherData> currentData;
            if (historicData.get(0).getDateTime() - System.currentTimeMillis() / 1000 < -1800) { //Data older than half an hour
                currentData = getAPIResponse(location.getLatitude(), location.getLongitude(), location.getPath());
            } else {
                currentData = historicData;
            }
            generateWarnings(historicData, currentData, location);
            weatherDataMap.put(location, currentData);
        }
        recentLocations = importLocations(Paths.get("data" + pSep + "recent.csv"));
        for (Location location : recentLocations) {
            List<StatusWeatherData> historicData = JsonIO.readJson(location.getPath());
            List<StatusWeatherData> currentData;
            if (historicData.get(0).getDateTime() - System.currentTimeMillis() / 1000 < -1800) { //Data older than half an hour
                currentData = getAPIResponse(location.getLatitude(), location.getLongitude(), location.getPath());
            } else {
                currentData = historicData;
            }
            weatherDataMap.put(location, currentData);
        }
    }

    /**
     * Returns the list of favourite locations
     *
     * @return favouriteLocations A list of favourite locations
     */
    public List<Location> getFavourites() {
        return favouriteLocations;
    }

    /**
     * Returns the list of recently accessed locations
     *
     * @return recentLocation A list of recent locations
     */
    public List<Location> getRecent() {
        return recentLocations;
    }

    /**
     * Fetches data on a stored location and updates the list of recently accessed locations
     * Updates the stored forecast if the data is more than 30 minutes old
     *
     * @param location The location for which the forecast is desired
     * @return List<StatusWeatherData> The weather forecast for that location
     */
    public List<StatusWeatherData> getForecast(Location location) {
        if (!(favouriteLocations.contains(location))) {
            if (recentLocations.contains(location)) {
                recentLocations.remove(location);
            }
            addToRecent(location);
        }
        if (weatherDataMap.get(location).get(0).getDateTime() - System.currentTimeMillis() / 1000 < -1800) {
            weatherDataMap.put(location, getAPIResponse(location.getLatitude(), location.getLongitude(),
                    location.getPath()));
        }
        return weatherDataMap.get(location);
    }

    /**
     * Adds a specified location to the favourites list
     *
     * @param location The location to add
     */
    public void makeFavourite(Location location) {
        if (recentLocations.contains(location)) {
            recentLocations.remove(location);
            favouriteLocations.add(0, location);
            location.toggleFavourite();
        }
        saveLocations();
    }

    /**
     * Removes a specified location from the favourites list
     *
     * @param location The location to remove
     */
    public void removeFavourite(Location location) {
        if (favouriteLocations.contains(location)) {
            favouriteLocations.remove(location);
            location.toggleFavourite();
            addToRecent(location);
        }
        saveLocations();
    }

    /**
     * Adds a specified location to the recent location list
     *
     * @param location The location to add
     */
    private void addToRecent(Location location) {
        if (!favouriteLocations.contains(location)) {
            recentLocations.add(0, location);
            if (recentLocations.size() > 100) {
                recentLocations = recentLocations.subList(0, 100);
            }
        }
    }

    /**
     * Removes a specified location from the recent location list
     *
     * @param location The location to remove
     */
    public void removeLocation(Location location) {
        if (favouriteLocations.contains(location)) {
            favouriteLocations.remove(location);
        } else if (recentLocations.contains(location)) {
            recentLocations.remove(location);
        }
        weatherDataMap.remove(location);
        saveLocations();
    }

    /**
     * Saves location data
     */
    private void saveLocations() {
        exportLocations(Paths.get("data" + pSep + "favourites.csv"), favouriteLocations);
        exportLocations(Paths.get("data" + pSep + "recent.csv"), recentLocations);
    }

    /**
     * Adds a new location to the data structure
     *
     * @param location The new location to add
     */
    public void addNewLocation(Location location) {
        weatherDataMap.put(location, getAPIResponse(location.getLatitude(), location.getLongitude(),
                location.getPath()));
        addToRecent(location);
        saveLocations();
    }

    /**
     * Gets a response from the API for a requested location
     *
     * @param latitude  The latitude of the location
     * @param longitude The longitude of the location
     * @param path      The path to save the weather data at
     * @return The forecast for that location
     */
    private List<StatusWeatherData> getAPIResponse(Float latitude, Float longitude, Path path) {
        try {
            String subUrl = String.format(Locale.ROOT, "find/station?lat=%f&lon=%f&cnt=%d&cluster=yes",
                    latitude, longitude, 1);
            JSONObject response = api.doQuery(subUrl);
            WeatherStatusResponse nearbyStation = new WeatherStatusResponse(response);
            List<StatusWeatherData> forecast = nearbyStation.getWeatherStatus();
            JsonIO.saveJson(path, response);
            return forecast;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Imports the save locations on disk on startup
     *
     * @param path The file to read the list of locations from
     * @return The list of locations stored in the provided file
     */
    private static List<Location> importLocations(Path path) {
        List<Location> locationsList = new LinkedList<>();
        try {
            BufferedReader r = Files.newBufferedReader(path);
            String line = r.readLine();
            while (!(line == null)) {
                String[] data = line.split(",");
                String name = data[0];
                float latitude = Float.parseFloat(data[1]);
                float longitude = Float.parseFloat(data[2]);
                boolean isFavourite = Boolean.parseBoolean(data[3]);
                Path locationPath = Paths.get(data[4]);
                WarningsContainer warnings = new WarningsContainer();
                for (int i = 5; i < data.length; i++) {
                    warnings.addWarning(
                            new Warning(WeatherData.WeatherCondition.ConditionCode.valueof(Integer.parseInt(data[i++])),
                                    data[i++]), Integer.parseInt(data[i]));
                }
                locationsList.add(new Location(latitude, longitude, isFavourite, locationPath, name, warnings));
                r.readLine();
            }
        } catch (IOException e) {
            try {
                Files.createFile(path);
            } catch (IOException f) {
                throw new RuntimeException("File system improperly configured");
            }
        }
        return locationsList;
    }

    /**
     * Saves all locations in a list to disk
     *
     * @param path      The file to save the locations to
     * @param locations The list of locations to save
     */
    private static void exportLocations(Path path, List<Location> locations) {
        try {
            BufferedWriter w = Files.newBufferedWriter(path);
            for (Location location : locations) {
                StringBuilder line = new StringBuilder();
                line.append(location.getName());
                line.append(",");
                line.append(location.getLatitude());
                line.append(",");
                line.append(location.getLongitude());
                line.append(",");
                line.append(location.isFavourite());
                line.append(",");
                line.append(location.getPath());
                line.append(",");
                WarningsContainer warnings = location.getAllWarnings();
                for (Warning warning : warnings.getAllChangeWarnings()) {
                    line.append(warning.getWarningType());
                    line.append(",");
                    line.append(warning.getDescription());
                    line.append(",0,");
                }
                for (Warning warning : warnings.getAllYellowWarnings()) {
                    line.append(warning.getWarningType());
                    line.append(",");
                    line.append(warning.getDescription());
                    line.append(",1,");
                }
                for (Warning warning : warnings.getAllAmberWarnings()) {
                    line.append(warning.getWarningType());
                    line.append(",");
                    line.append(warning.getDescription());
                    line.append(",2,");
                }
                for (Warning warning : warnings.getAllRedWarnings()) {
                    line.append(warning.getWarningType());
                    line.append(",");
                    line.append(warning.getDescription());
                    line.append(",3,");
                }
                line.deleteCharAt(line.length() - 1);
                line.append("\n");
                w.write(line.toString());
            }
        } catch (IOException e) {
            throw new RuntimeException("File system improperly configured");
        }
    }

    /**
     * Generates weather warnings
     *
     * @param oldForecast The historic forecast on disk
     * @param newForecast The new forecast from the API response
     * @param location    The location to evaluate the warnings for
     */
    private void generateWarnings(List<StatusWeatherData> oldForecast, List<StatusWeatherData> newForecast,
                                  Location location) {
        int oldIndex = 0;
        while (oldForecast.get(oldIndex).getDateTime() < newForecast.get(0).getDateTime()) {
            oldIndex++;
        }
        for (int newIndex = 0; newIndex < oldForecast.size(); oldIndex++, newIndex++) {
            if (oldForecast.get(oldIndex).getTemp() - newForecast.get(newIndex).getTemp() > 7) {
                location.addWarning(new Warning(WeatherData.WeatherCondition.ConditionCode.COLD,
                        "The forecast is colder than previously forecast"), 0);
            }
            if (oldForecast.get(oldIndex).getTemp() - newForecast.get(newIndex).getTemp() < -7) {
                location.addWarning(new Warning(WeatherData.WeatherCondition.ConditionCode.HOT,
                        "The forecast is colder than previously forecast"), 0);
            }
            if (newForecast.get(newIndex).getWind().getSpeed() - oldForecast.get(oldIndex).getWind().getSpeed() > 10) {
                location.addWarning(new Warning(WeatherData.WeatherCondition.ConditionCode.WINDY,
                        "The forecast is windier than previously forecast"), 0);
            }
            List<WeatherData.WeatherCondition.ConditionCode> forecastConditionGroup = new LinkedList<>();
            for (List<WeatherData.WeatherCondition.ConditionCode> group : weatherGroupings) {
                int correctGroup = 0;
                for (WeatherData.WeatherCondition condition : newForecast.get(newIndex).getWeatherConditions()) {
                    if (group.contains(condition.getCode())) {
                        correctGroup++;
                    }
                }
                if (correctGroup > newForecast.get(newIndex).getWeatherConditions().size() / 2) {
                    forecastConditionGroup = group;
                    break;
                }
            }
            if (forecastConditionGroup.size() > 0) {
                int correctGroup = 0;
                for (WeatherData.WeatherCondition condition : oldForecast.get(oldIndex).getWeatherConditions()) {
                    if (forecastConditionGroup.contains(condition.getCode())) {
                        correctGroup++;
                    }
                }
                if (correctGroup < oldForecast.get(oldIndex).getWeatherConditions().size() / 2) {
                    if (!(forecastConditionGroup.get(0) == WeatherData.WeatherCondition.ConditionCode.UNKNOWN)) {
                        location.addWarning(new Warning(forecastConditionGroup.get(0),
                                        "The forecast conditions are different to those previously forecast"),
                                0);
                    }
                }
            }
        }

        // Randomly generates more serious weather warning for now as a test since a suitable API could not be found
        Random rnd = new Random();
        rnd.setSeed(0L);
        if (rnd.nextInt(100) < 5) {
            int severity = rnd.nextInt(100);
            if (severity > 95) {
                severity = 3;
            } else if (severity > 60) {
                severity = 2;
            } else {
                severity = 1;
            }

            switch (rnd.nextInt(4)) {
                case 0:
                    location.addWarning(new Warning(WeatherData.WeatherCondition.ConditionCode.WINDY,
                            "A Met Office " + severityDescriptor.get(severity) +
                                    " warning of wind has been issued"), severity);
                case 1:
                    location.addWarning(new Warning(WeatherData.WeatherCondition.ConditionCode.SNOW,
                            "A Met Office " + severityDescriptor.get(severity) +
                                    " warning of snow has been issued"), severity);
                case 2:
                    location.addWarning(new Warning(WeatherData.WeatherCondition.ConditionCode.HEAVY_INTENSITY_RAIN,
                            "A Met Office " + severityDescriptor.get(severity) +
                                    " warning of rain has been issued"), severity);
                case 3:
                    location.addWarning(new Warning(WeatherData.WeatherCondition.ConditionCode.FOG,
                            "A Met Office " + severityDescriptor.get(severity) +
                                    " warning of wind has been issued"), severity);
                case 4:
                    location.addWarning(new Warning(WeatherData.WeatherCondition.ConditionCode.COLD,
                            "A Met Office " + severityDescriptor.get(severity) +
                                    " warning of ice has been issued"), severity);
            }
        }
    }

}
