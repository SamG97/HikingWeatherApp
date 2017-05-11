package uk.ac.cam.group7.interaction_design.hiking_app;

public class TimeStamp {

    private final int hours;
    private final int minutes;

    public TimeStamp(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimeStamp timeStamp = (TimeStamp) o;

        if (hours != timeStamp.hours) return false;
        return minutes == timeStamp.minutes;
    }

    @Override
    public int hashCode() {
        int result = hours;
        result = 31 * result + minutes;
        return result;
    }

    @Override
    public String toString() {
        return hours + ":" + minutes;
    }

}
