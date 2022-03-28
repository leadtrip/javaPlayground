package wood.mike.versions.java15.bike;

public final class MountainBike implements Bike {

    @Override
    public String handleBarType() {
        return "flat";
    }

    @Override
    public boolean getAero() {
        return false;
    }

    @Override
    public boolean jump() {
        return true;
    }
}
