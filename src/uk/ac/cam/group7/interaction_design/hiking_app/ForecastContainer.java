package uk.ac.cam.group7.interaction_design.hiking_app;

import org.bitpipeline.lib.owm.*;
import org.json.JSONException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ForecastContainer {

    private final static String pSep = File.pathSeparator;
    private final static OwmClient api = new OwmClient();

    private Map<Location, StatusWeatherData> weatherDataMap;
    private List<Location> favouriteLocations;
    private List<Location> recentLocations;

    static {
        api.setAPPID("d12c4a04b7d0170dff8f1afca1e4c0ff");
    }

    public ForecastContainer() {
        weatherDataMap = new HashMap<>();
        favouriteLocations = importLocations(Paths.get("data" + pSep + "favourites"));
        for (Location location : favouriteLocations) {
            weatherDataMap.put(location, JsonReader.readJson(location.getPath()));
        }
        recentLocations = importLocations(Paths.get("data" + pSep + "recent"));
        for (Location location : recentLocations) {
            weatherDataMap.put(location, JsonReader.readJson(location.getPath()));
        }
    }

    public List<Location> getFavourites() {
        return favouriteLocations;
    }

    public List<Location> getRecent() {
        return recentLocations;
    }

    public StatusWeatherData getForecast(Location location) {
        return weatherDataMap.get(location);
    }

    public void makeFavourite(Location location) {
        if (recentLocations.contains(location)) {
            recentLocations.remove(location);
            favouriteLocations.add(0, location);
        }
    }

    public void addToRecent(Location location) {
        if(!favouriteLocations.contains(location)) {
            recentLocations.add(0, location);
            if (recentLocations.size() > 100) {
                recentLocations = recentLocations.subList(0, 100);
            }
        }
    }

    public void saveLocations() {
        exportLocations(Paths.get("data" + pSep + "favourites"), favouriteLocations);
        exportLocations(Paths.get("data" + pSep + "recent"), recentLocations);
    }

    private static StatusWeatherData getAPIResponse(float latitude, float longitude) {
        try {
            WeatherStatusResponse nearbyStation = api.currentWeatherAroundPoint(latitude, longitude, 1);
            StatusWeatherData forecast = nearbyStation.getWeatherStatus().get(0);
            //TODO: Save weather data as JSON
            return forecast;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static List<Location> importLocations(Path path) {
        List<Location> locationsList = new LinkedList<>();
        try {
            Files.createFile(path);
            BufferedReader r = Files.newBufferedReader(path);
            String line = r.readLine();
            while (!(line == null)) {
                String[] data = line.split(",");
                String name = data[0];
                double latitude = Double.parseDouble(data[1]);
                double longitude = Double.parseDouble(data[2]);
                boolean isFavourite = Boolean.parseBoolean(data[3]);
                Path locationPath = Paths.get(data[4]);
                locationsList.add(new Location(latitude, longitude, isFavourite, locationPath, name));
                r.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("File system improperly configured");
        }
        return locationsList;
    }

    private static void exportLocations(Path path, List<Location> locations) {
        List<Location> locationsList = new LinkedList<>();
        try {
            BufferedWriter w = Files.newBufferedWriter(path);
            for (Location location : locations) {
                StringBuilder line = new StringBuilder();
                line.append(location.getName());
                line.append(location.getLatitude());
                line.append(location.getLongitude());
                line.append(location.isFavourite());
                line.append(location.getPath());
                line.append("\n");
                w.write(line.toString());
            }
        } catch (IOException e) {
            throw new RuntimeException("File system improperly configured");
        }
    }

}
