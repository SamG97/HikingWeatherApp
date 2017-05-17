package uk.ac.cam.group7.interaction_design.hiking_app;

import java.util.LinkedList;
import java.util.List;

public class WarningsContainer {

    private List<Warning> change;
    private List<Warning> yellow;
    private List<Warning> amber;
    private List<Warning> red;

    public WarningsContainer() {
        change = new LinkedList<>();
        yellow = new LinkedList<>();
        amber = new LinkedList<>();
        red = new LinkedList<>();
    }

    public List<Warning> getAllChangeWarnings() {
        return change;
    }

    public List<Warning> getAllYellowWarnings() {
        return yellow;
    }

    public List<Warning> getAllAmberWarnings() {
        return amber;
    }

    public List<Warning> getAllRedWarnings() {
        return red;
    }

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

    public void addWarning(Warning warning, int severity) {
        switch (severity) {
            case 0: change.add(0, warning);
            case 1: yellow.add(0, warning);
            case 2: amber.add(0, warning);
            case 3: red.add(0, warning);
            default: throw new IllegalArgumentException("May only have a severity of 0 - 3 inclusive");
        }
    }

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
