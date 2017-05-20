package uk.ac.cam.group7.interaction_design.hiking_app.backend;

import java.util.LinkedList;
import java.util.List;

/**
 * Container for all warnings for a location, sorted into groups of severity
 *
 * @author Sam Gooch
 */
class WarningsContainer {

    private List<Warning> change;
    private List<Warning> yellow;
    private List<Warning> amber;
    private List<Warning> red;

    /**
     * Initialises the relevant data structures when created
     */
    WarningsContainer() {
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
    List<Warning> getAllChangeWarnings() {
        return change;
    }

    /**
     * Getter for all yellow weather warnings
     *
     * @return List<Warning> Yellow weather warnings
     */
    List<Warning> getAllYellowWarnings() {
        return yellow;
    }

    /**
     * Getter for all amber weather warnings
     *
     * @return List<Warning> Amber weather warnings
     */
    List<Warning> getAllAmberWarnings() {
        return amber;
    }

    /**
     * Getter for all red weather warnings
     *
     * @return List<Warning> Red weather warnings
     */
    List<Warning> getAllRedWarnings() {
        return red;
    }

    /**
     * Get the next warning to display
     *
     * @return Warning to display
     */
    Warning getNextWarning() {
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

    int getSeverityOfNextWarning() {
        if (red.size() > 0) {
            return 3;
        }
        if (amber.size() > 0) {
            return 2;
        }
        if (yellow.size() > 0) {
            return 1;
        }
        if (change.size() > 0) {
            return 0;
        }
        return -1;
    }

    /**
     * Add a new warning to the location
     *
     * @param warning  The warning to add
     * @param severity The severity of the warning
     */
    void addWarning(Warning warning, int severity) {
        if (severity == 0) {
            change.add(0, warning);
        } else if (severity == 1) {
            yellow.add(0, warning);
        } else if (severity == 2) {
            amber.add(0, warning);
        } else if (severity == 3) {
            red.add(0, warning);
        }
    }

    /**
     * Acknowledges that a warning has been seen and removes it from the data structure so that it is not seen again
     */
    void acknowledgeWarning() {
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
