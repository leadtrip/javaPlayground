package wood.mike.design.behavioral;

/**
 * Break algorithm down into sequence of steps then provide a method that will execute the steps in the required order.
 * Implementors must implement the abstract steps with option to implement non-abstract but not required steps too.
 */
public class Template {
    public static void main(String[] args) {
        BikeTemplate mbTemplate = new MountainBike();
        mbTemplate.buildBike();

        BikeTemplate bmxTemplate = new BMX();
        bmxTemplate.buildBike();
    }
}

/**
 * Abstract template method class
 */
abstract class BikeTemplate {

    // final method
    final void buildBike() {
        addWheels();
        addTyres();
        addHandleBars();
        addCrank();
        addChain();
        addGears();
        addAccessories();
    }

    // these methods MUST be implemented by subclasses
    abstract void addTyres();
    abstract void addChain();
    abstract void addCrank();
    abstract void addHandleBars();
    abstract void addWheels();

    // optional hook method
    public void addGears() {

    }

    // optional hook method
    public void addAccessories() {

    }
}

/**
 * BMX doesn't have gears, no need to implement hook methods
 */
class BMX extends BikeTemplate{

    @Override
    void addTyres() {
        System.out.println( "Adding Cult x vans tyres" );
    }

    @Override
    void addChain() {
        System.out.println( "Adding single speed chain" );
    }

    @Override
    void addCrank() {
        System.out.println( "Adding Box crank" );
    }

    @Override
    void addHandleBars() {
        System.out.println( "Adding Blank niner XL bars" );
    }

    @Override
    void addWheels() {
        System.out.println( "Adding 20' Seal wheels" );
    }
}

/**
 * Mountain bike implements both hook methods for gears and accessories
 */
class MountainBike extends BikeTemplate {
    @Override
    void addTyres() {
        System.out.println("Adding Continental Der Kaiser tyres");
    }

    @Override
    void addChain() {
        System.out.println("Adding SLX 12 speed chain");
    }

    @Override
    void addCrank() {
        System.out.println("Adding XTR 2 x 12 168mm crank");
    }

    @Override
    void addHandleBars() {
        System.out.println("Adding flat bars");
    }

    @Override
    void addWheels() {
        System.out.println("Adding Halo wheels");
    }

    @Override
    public void addGears() {
        System.out.println( "Adding XTR 9100 shifters" );
    }

    @Override
    public void addAccessories() {
        System.out.println( "Adding bottle cage" );;
    }
}

