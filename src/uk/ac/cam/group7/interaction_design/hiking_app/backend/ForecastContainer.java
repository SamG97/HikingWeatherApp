package uk.ac.cam.group7.interaction_design.hiking_app.backend;

import org.bitpipeline.lib.owm.OwmClient;
import org.bitpipeline.lib.owm.StatusWeatherData;
import org.bitpipeline.lib.owm.WeatherData;
import org.bitpipeline.lib.owm.WeatherStatusResponse;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Stores all locations and forecasts in a single format and provides an interface to access this data in a helpful way
 * Singleton class to prevent multiple instantiation
 *
 * @author Sam Gooch, Dávid
 */
public class ForecastContainer {

    private final static ForecastContainer reference = new ForecastContainer();

    private final static OwmClient api = new OwmClient();

    static {
        api.setAPPID("d12c4a04b7d0170dff8f1afca1e4c0ff"); // API key for our application
    }

    private Map<Location, List<StatusWeatherData>> weatherDataMap;
    private List<Location> favouriteLocations;
    private List<Location> recentLocations;

    /**
     * Loads previously saved data and tries to contact the API for fresh data
     * Generates warnings for the old data if it has changed
     */
    private ForecastContainer() {
        weatherDataMap = new HashMap<>();
        favouriteLocations = importLocations(Paths.get("data/favourites.csv"));
        for (Location location : favouriteLocations) {
            List<StatusWeatherData> historicData = JsonIO.readJson(location.getPath());
            List<StatusWeatherData> currentData;
            // Checks for 'freshness' off data
            if (!(api == null) && historicData.get(0).getDateTime() - System.currentTimeMillis() / 1000 < -1800) {
                currentData = getAPIResponse(location.getLatitude(), location.getLongitude(), location.getPath());
            } else {
                currentData = historicData;
            }
            generateWarnings(historicData, currentData, location);
            weatherDataMap.put(location, currentData);
        }
        recentLocations = importLocations(Paths.get("data/recent.csv"));
        for (Location location : recentLocations) {
            List<StatusWeatherData> historicData = JsonIO.readJson(location.getPath());
            List<StatusWeatherData> currentData;
            // Checks for 'freshness' off data
            if (!(api == null) && historicData.get(0).getDateTime() - System.currentTimeMillis() / 1000 < -1800) {
                currentData = getAPIResponse(location.getLatitude(), location.getLongitude(), location.getPath());
            } else {
                currentData = historicData;
            }
            weatherDataMap.put(location, currentData);
        }
        saveLocations();
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
        if (!(api == null) && weatherDataMap.get(location).get(0).getDateTime() - (System.currentTimeMillis() / 1000)
                < -1800) {
            weatherDataMap.put(location, getAPIResponse(location.getLatitude(), location.getLongitude(),
                    location.getPath()));
        } else if (!weatherDataMap.containsKey(location)) {
            weatherDataMap.put(location, null);
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
     * Renames a location and saves the change
     *
     * @param location Location to update
     * @param name     New name for the location
     */
    public void renameLocation(Location location, String name) {
        location.setName(name);
        saveLocations();
    }

    public void acknowledgeWarning(Location location) {
        location.acknowledgeWarning();
        saveLocations();
    }

    /**
     * Saves location data
     */
    private void saveLocations() {
        exportLocations(Paths.get("data/favourites.csv"), favouriteLocations);
        exportLocations(Paths.get("data/recent.csv"), recentLocations);
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
            String subUrl = String.format(Locale.ROOT, "forecast?lat=%f&lon=%f&",
                    latitude, longitude);
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
    private List<Location> importLocations(Path path) {
        List<Location> locationsList = new LinkedList<>();
        try {
            BufferedReader r = Files.newBufferedReader(path);
            String line = r.readLine();
            while (!(line == null)) {
                String[] data = line.split(",");
                String name = data[0];
                if (name.contains(";")) {
                    String[] parts = name.split(";");
                    StringBuilder newName = new StringBuilder();
                    for (String part : parts) {
                        newName.append(part);
                        newName.append(",");
                    }
                    newName.deleteCharAt(newName.length() - 1);
                    name = newName.toString();
                }
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
                line = r.readLine();
            }
            r.close();
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
    private void exportLocations(Path path, List<Location> locations) {
        try {
            BufferedWriter w = Files.newBufferedWriter(path);
            for (Location location : locations) {
                if (!weatherDataMap.containsKey(location) || weatherDataMap.get(location) == null) {
                    continue;
                }
                StringBuilder line = new StringBuilder();
                String name = location.getName();
                if (name.contains(",")) {
                    String[] parts = name.split(",");
                    StringBuilder newName = new StringBuilder();
                    for (String part : parts) {
                        newName.append(part);
                        newName.append(";");
                    }
                    newName.deleteCharAt(newName.length() - 1);
                    name = newName.toString();
                }
                line.append(name);
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
                    line.append(warning.getWarningType().getId());
                    line.append(",");
                    line.append(warning.getDescription());
                    line.append(",0,");
                }
                for (Warning warning : warnings.getAllYellowWarnings()) {
                    line.append(warning.getWarningType().getId());
                    line.append(",");
                    line.append(warning.getDescription());
                    line.append(",1,");
                }
                for (Warning warning : warnings.getAllAmberWarnings()) {
                    line.append(warning.getWarningType().getId());
                    line.append(",");
                    line.append(warning.getDescription());
                    line.append(",2,");
                }
                for (Warning warning : warnings.getAllRedWarnings()) {
                    line.append(warning.getWarningType().getId());
                    line.append(",");
                    line.append(warning.getDescription());
                    line.append(",3,");
                }
                line.deleteCharAt(line.length() - 1);
                line.append("\n");
                w.write(line.toString());
            }
            w.close();
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
                        "Colder than previously forecast"), 0);
            }
            if (oldForecast.get(oldIndex).getTemp() - newForecast.get(newIndex).getTemp() < -7) {
                location.addWarning(new Warning(WeatherData.WeatherCondition.ConditionCode.HOT,
                        "Hotter than previously forecast"), 0);
            }
            if (newForecast.get(newIndex).getWind().getSpeed() - oldForecast.get(oldIndex).getWind().getSpeed() > 10) {
                location.addWarning(new Warning(WeatherData.WeatherCondition.ConditionCode.WINDY,
                        "Windier than previously forecast"), 0);
            }
            List<WeatherData.WeatherCondition.ConditionCode> forecastConditionGroup = new LinkedList<>();
            for (List<WeatherData.WeatherCondition.ConditionCode> group : WeatherGroupings.getGroups()) {
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
                                        "Weather conditions have changed"),
                                0);
                    }
                }
            }
        }

        // Randomly generates more serious weather warning for now as a test since a suitable API could not be found
        Random rnd = new Random(location.hashCode());
        if (rnd.nextInt(100) <= 5) {
            int severity = rnd.nextInt(100);
            if (severity > 95) {
                severity = 3;
            } else if (severity > 60) {
                severity = 2;
            } else {
                severity = 1;
            }

            Map<Integer, String> severityDescriptor = WeatherGroupings.getSeverity();
            int warningType = rnd.nextInt(5);
            if (warningType == 1) {
                location.addWarning(new Warning(WeatherData.WeatherCondition.ConditionCode.WINDY,
                        severityDescriptor.get(severity) + " warning of wind issued"), severity);
            } else if (warningType == 2) {
                location.addWarning(new Warning(WeatherData.WeatherCondition.ConditionCode.SNOW,
                        severityDescriptor.get(severity) + " warning of snow issued"), severity);
            } else if (warningType == 3) {
                location.addWarning(new Warning(WeatherData.WeatherCondition.ConditionCode.HEAVY_INTENSITY_RAIN,
                        severityDescriptor.get(severity) + " warning of rain issued"), severity);
            } else if (warningType == 4) {
                location.addWarning(new Warning(WeatherData.WeatherCondition.ConditionCode.FOG,
                        severityDescriptor.get(severity) + " warning of wind issued"), severity);
            } else if (warningType == 5) {
                location.addWarning(new Warning(WeatherData.WeatherCondition.ConditionCode.COLD,
                        severityDescriptor.get(severity) + " warning of ice issued"), severity);
            }
        }
    }

}
