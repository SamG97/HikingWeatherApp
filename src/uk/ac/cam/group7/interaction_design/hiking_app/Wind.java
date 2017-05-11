package uk.ac.cam.group7.interaction_design.hiking_app;

public class Wind {

    private final double speed;
    private final double direction;

    public Wind(double speed, double direction) {
        this.speed = speed;
        this.direction = direction;
    }

    public double getSpeed() {
        return speed;
    }

    public double getDirection() {
        return direction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Wind wind = (Wind) o;

        if (Double.compare(wind.speed, speed) != 0) return false;
        return Double.compare(wind.direction, direction) == 0;
    }

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
