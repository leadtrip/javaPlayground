package wood.mike.java15;

import wood.mike.java15.bike.Bike;
import wood.mike.java15.bike.MountainBike;
import wood.mike.java15.bike.RoadBike;

import java.util.ArrayList;
import java.util.List;

public class Java15Features {
    public static void main(String[] args) {
        sealedClasses();
    }

    /**
     * Sealed classes and interfaces restrict which other classes or interfaces may extend or implement them.
     */
    private static void sealedClasses() {
        List<Bike> bikes = new ArrayList<>();
        bikes.add(new RoadBike());
        bikes.add(new MountainBike());
        //bikes.add(new EBike());   EBike is not a Bike in the sealed hierarchy

        bikes.stream()
            .filter(Java15Features::suitableForRoughTerrain)
            .forEach(System.out::println);
    }

    /**
     * Because we know the complete set of bikes available our switch statement can
     * cover all possibilities and there is no need for a default clause
     */
    private static boolean suitableForRoughTerrain( Bike bike ) {
        return switch (bike) {
            case RoadBike rb -> rb.jump();
            case MountainBike mb -> mb.jump();
        };
    }
}
