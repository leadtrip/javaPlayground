package wood.mike.java15.bike;

public final class RoadBike implements Bike {

    @Override
    public String handleBarType() {
        return "drop";
    }

    @Override
    public boolean getAero() {
        return true;
    }

    @Override
    public boolean jump() {
        return false;
    }
}
