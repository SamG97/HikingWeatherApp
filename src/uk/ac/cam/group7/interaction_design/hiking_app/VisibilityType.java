package uk.ac.cam.group7.interaction_design.hiking_app;

import java.util.HashMap;
import java.util.Map;

public enum VisibilityType {
    TERRIBLE(0), VERY_POOR(1), POOR(2), AVERAGE(3), GOOD(4), VERY_GOOD(5), EXCELLENT(6);

    private static Map<Integer, VisibilityType> types = new HashMap<>();
    static {
        for (VisibilityType type : VisibilityType.values()) {
            types.put(type.name, type);
        }
    }

    private int name;

    private VisibilityType(int name) {
        this.name = name;
    }

    public String toString() {
        return String.valueOf(name);
    }

    public static VisibilityType valueOf(int name) {
        return types.get(name);
    }
}
