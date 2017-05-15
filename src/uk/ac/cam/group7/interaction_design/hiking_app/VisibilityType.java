package uk.ac.cam.group7.interaction_design.hiking_app;

import java.util.HashMap;
import java.util.Map;

/**
 * Stores an encoding for the visibility data
 * @author Sam Gooch
 */
public enum VisibilityType {
    TERRIBLE(0), VERY_POOR(1), POOR(2), AVERAGE(3), GOOD(4), VERY_GOOD(5), EXCELLENT(6);

    private static Map<Integer, VisibilityType> types = new HashMap<>();
    static {
        for (VisibilityType type : VisibilityType.values()) {
            types.put(type.name, type);
        }
    }

    private int name;

    /**
     * Private constructor for VisibilityType
     * @param name The value to be encoded to a VisibilityType
     */
    private VisibilityType(int name) {
        this.name = name;
    }

    /**
     * Converts VisibilityType to a String
     * @return String Value of VisibilityType
     */
    public String toString() {
        return String.valueOf(name);
    }

    /**
     * Finds value as VisibilityType of an un-encoded input
     * @param name The un-encoded value
     * @return VisibilityType The encoded value of name
     */
    public static VisibilityType valueOf(int name) {
        return types.get(name);
    }

}
