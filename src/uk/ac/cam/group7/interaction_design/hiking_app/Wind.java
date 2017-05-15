package uk.ac.cam.group7.interaction_design.hiking_app;

/**
 * Stores the data relating to wind
 * @author Sam Gooch
 */
public class Wind {

    private final double speed;
    private final double direction;

    /**
     * Constructor for Wind
     * @param speed The wind speed (in mph)
     * @param direction The wind direction (bearing in degrees)
     */
    public Wind(double speed, double direction) {
        this.speed = speed;
        this.direction = direction;
    }

    /**
     * Getter for wind speed
     * @return speed The wind speed (in mph)
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Getter for wind direction
     * @return direction The wind direction (bearing in degrees)
     */
    public double getDirection() {
        return direction;
    }

    /**
     * Checks if two winds are equal
     * @param o The wind to compare to
     * @return boolean true if two winds are equal; false if not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Wind wind = (Wind) o;

        if (Double.compare(wind.speed, speed) != 0) return false;
        return Double.compare(wind.direction, direction) == 0;
    }

    /**
     * Hashes the wind data for storage in hash based data structures
     * @return int The hash value for the wind data
     */
    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(speed);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(direction);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

}
