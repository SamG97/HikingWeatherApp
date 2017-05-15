package uk.ac.cam.group7.interaction_design.hiking_app;

import java.util.HashMap;
import java.util.Map;

/**
 * Stores an encoding for the possible differences in forecasts
 * @author Sam Gooch
 */
public enum DifferentType {
    NONE(0), TEMPERATURE(1), WIND(2), TYPE(3);

    private static Map<Integer, DifferentType> types = new HashMap<>();
    static {
        for (DifferentType type : DifferentType.values()) {
            types.put(type.name, type);
        }
    }

    private int name;

    /**
     * Private constructor for DifferentType
     * @param name The value to be encoded to a VisibilityType
     */
    private DifferentType(int name) {
        this.name = name;
    }

    /**
     * Converts DifferentType to a String
     * @return String Value of VisibilityType
     */
    public String toString() {
        return String.valueOf(name);
    }

    /**
     * Finds value as DifferentType of an un-encoded input
     * @param name The un-encoded value
     * @return DifferentType The encoded value of name
     */
    public static DifferentType valueOf(int name) {
        return types.get(name);
    }

}
