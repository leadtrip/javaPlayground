package wood.mike.versions.java15.bike;

/**
 * EBike IS NOT ALLOWED IN THE SEALED HIERARCHY
 * relevant parts commented out to allow compliation
 * class is not allowed to extend sealed class: wood.mike.java15.bike.Bike (as it is not listed in its permits clause)
 */
public class EBike //implements Bike
         {
    //@Override
    public String handleBarType() {
        return null;
    }

    //@Override
    public boolean getAero() {
        return false;
    }

    //@Override
    public boolean jump() {
        return false;
    }
}
