package uk.ac.cam.group7.interaction_design.hiking_app;

public class DateStamp {

    private final int year;
    private final int month;
    private final int day;
    private final int hour;

    public DateStamp(int year, int month, int day, int hour) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DateStamp timeStamp = (DateStamp) o;

        if (year != timeStamp.year) return false;
        if (month != timeStamp.month) return false;
        if (day != timeStamp.day) return false;
        return hour == timeStamp.hour;
    }

    @Override
    public int hashCode() {
        int result = year;
        result = 31 * result + month;
        result = 31 * result + day;
        result = 31 * result + hour;
        return result;
    }
}
