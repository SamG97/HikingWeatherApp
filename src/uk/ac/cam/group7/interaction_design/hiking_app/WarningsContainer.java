package uk.ac.cam.group7.interaction_design.hiking_app;

import java.util.LinkedList;
import java.util.List;

/**
 * Container for all warnings for a location, sorted into groups of severity
 *
 * @author Sam Gooch
 */
public class WarningsContainer {

    private List<Warning> change;
    private List<Warning> yellow;
    private List<Warning> amber;
    private List<Warning> red;

    /**
     * Initialises the relevant data structures when created
     */
    public WarningsContainer() {
        change = new LinkedList<>();
        yellow = new LinkedList<>();
        amber = new LinkedList<>();
        red = new LinkedList<>();
    }

    /**
     * Getter for all warnings of a change in forecast
     *
     * @return List<Warning> Changed forecasts
     */
    public List<Warning> getAllChangeWarnings() {
        return change;
    }

    /**
     * Getter for all yellow weather warnings
     *
     * @return List<Warning> Yellow weather warnings
     */
    public List<Warning> getAllYellowWarnings() {
        return yellow;
    }

    /**
     * Getter for all amber weather warnings
     *
     * @return List<Warning> Amber weather warnings
     */
    public List<Warning> getAllAmberWarnings() {
        return amber;
    }

    /**
     * Getter for all red weather warnings
     *
     * @return List<Warning> Red weather warnings
     */
    public List<Warning> getAllRedWarnings() {
        return red;
    }

    /**
     * Get the next warning to display
     *
     * @return Warning to display
     */
    public Warning getNextWarning() {
        if (red.size() > 0) {
            return red.get(0);
        }
        if (amber.size() > 0) {
            return amber.get(0);
        }
        if (yellow.size() > 0) {
            return yellow.get(0);
        }
        if (change.size() > 0) {
            return change.get(0);
        }
        return null;
    }

    /**
     * Add a new warning to the location
     *
     * @param warning  The warning to add
     * @param severity The severity of the warning
     */
    public void addWarning(Warning warning, int severity) {
        switch (severity) {
            case 0:
                change.add(0, warning);
            case 1:
                yellow.add(0, warning);
            case 2:
                amber.add(0, warning);
            case 3:
                red.add(0, warning);
            default:
                throw new IllegalArgumentException("May only have a severity of 0 - 3 inclusive");
        }
    }

    /**
     * Acknowledges that a warning has been seen and removes it from the data structure so that it is not seen again
     */
    public void acknowledgeWarning() {
        if (red.size() > 0) {
            red.remove(0);
            return;
        }
        if (amber.size() > 0) {
            amber.remove(0);
            return;
        }
        if (yellow.size() > 0) {
            yellow.remove(0);
            return;
        }
        if (change.size() > 0) {
            change.remove(0);
        }
    }

}
