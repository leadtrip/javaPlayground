package wood.mike.versions.java15.bike;

/**
 * Only road and mountain bikes are allowed in this hierarchy
 */
public sealed interface Bike permits RoadBike, MountainBike {
    String handleBarType();

    boolean getAero();

    boolean jump();
}
