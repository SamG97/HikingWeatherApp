package uk.ac.cam.group7.interaction_design.hiking_app.backend;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Holds information about the location
 * 2 float for Lat and Long (final)
 * String for name
 * boolean for favourite
 * Warnings container for all warnings
 *
 * @author dobrik, Sam Gooch
 */
public class Location {
    private final float latitude, longitude;
    private final Path path;
    private String name;
    private boolean isFavourite;
    private WarningsContainer warnings;

    /**
     * Copy constructor for location
     *
     * @param location Location to copy
     */
    public Location(Location location) {
        this.latitude = location.latitude;
        this.longitude = location.longitude;
        this.name = location.name;
        this.isFavourite = location.isFavourite;
        this.path = location.path;
        this.warnings = location.warnings;
    }

    /**
     * Constructor that takes no name parameter
     * and makes a new Location class with these coordinates
     *
     * @param latitude  Latitude of location
     * @param longitude Longitude of location
     */
    public Location(float latitude, float longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = latitude + ", " + longitude;
        this.isFavourite = false;
        this.path = Paths.get("data/" + hashCode() + ".json");
        this.warnings = new WarningsContainer();
    }

    /**
     * Constructor that takes no name parameter
     * and makes a new Location class with these coordinates
     *
     * @param latitude    Latitude of location
     * @param longitude   Longitude of location
     * @param isFavourite If location is a favourite
     * @param path        The path to the JSON file storing the raw forecast data
     */
    public Location(float latitude, float longitude, boolean isFavourite, Path path, String name,
                    WarningsContainer warnings) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.isFavourite = isFavourite;
        this.path = path;
        this.warnings = warnings;
    }

    /**
     * Generates a filename for a location based on a hash of its latitude and longitude
     *
     * @return The hash code for the location
     */
    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(latitude);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    /**
     * Getter for latitude
     *
     * @return latitude (double)
     */
    public float getLatitude() {
        return latitude;
    }

    /**
     * Getter for longitude
     *
     * @return longitude (double)
     */
    public float getLongitude() {
        return longitude;
    }

    /**
     * Getter for name
     *
     * @return Name of location
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the name of the location
     *
     * @param name Name of the location
     */
    protected void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for favourite
     *
     * @return boolean for isFavourite or not
     */
    public boolean isFavourite() {
        return isFavourite;
    }

    /**
     * Set favourite location
     * boolean (false:notFav, true:Fav)
     */
    void toggleFavourite() {
        isFavourite = !isFavourite;
    }

    /**
     * Gets the path of the file storing the weather data for the location
     *
     * @return The path to the file
     */
    Path getPath() {
        return path;
    }

    /**
     * Gets the container to all weather warnings for the location
     *
     * @return WarningsContainer for the location
     */
    WarningsContainer getAllWarnings() {
        return warnings;
    }

    /**
     * Adds a new warning to the location
     *
     * @param warning  The warning to add
     * @param severity The severity of the warning
     */
    void addWarning(Warning warning, int severity) {
        warnings.addWarning(warning, severity);
    }

    /**
     * Gets the next warning to display
     *
     * @return Warning to display
     */
    public Warning getTopWarning() {
        return warnings.getNextWarning();
    }

    public int getWarningIconCode() {
        return warnings.getSeverityOfNextWarning();
    }

    /**
     * Acknowledges that the top warning has been seen so delete it
     */
    void acknowledgeWarning() {
        warnings.acknowledgeWarning();
    }

    /**
     * Checks for equality for two locations
     *
     * @param o Object to compare to
     * @return true or false depending on equality
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        return (longitude == location.longitude) && (latitude == location.latitude);
    }

    /**
     * Allows a String output for the location during debugging
     *
     * @return The String representation of a location
     */
    @Override
    public String toString() {
        return getName();
    }

}
