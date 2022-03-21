package wood.mike.design.structural.decorator;

/**
 * Allows behavior to be added to an individual object, dynamically, without affecting the behavior of other objects from the same class.
 * The decorator pattern is often useful for adhering to the Single Responsibility Principle, as it allows functionality to be divided
 * between classes with unique areas of concern.
 * Decorator use can be more efficient than subclassing, because an object's behavior can be augmented without defining an entirely new object.
 */
public class Decorator {
    public static void main(String[] args) {
        Shed insulatedShed = new InsulatedShed( new BasicShed() );
        insulatedShed.build();

        Shed insulatedElectricShed = new ElectricShed( new InsulatedShed( new BasicShed() ) );
        insulatedElectricShed.build();
    }
}

// component interface
interface Shed {
    void build();
}

// component implementation
class BasicShed implements Shed {
    @Override
    public void build() {
        System.out.println( "Building basic shed" );
    }
}

// decorator
class ShedDecorator implements Shed {

    protected Shed shed;

    public ShedDecorator(Shed shed) {
        this.shed = shed;
    }

    @Override
    public void build() {
        this.shed.build();
    }
}

// concrete decorator 1
class InsulatedShed extends ShedDecorator{

    public InsulatedShed(Shed shed) {
        super(shed);
    }

    @Override
    public void build() {
        super.build();
        System.out.println( "Insulating shed" );
    }
}

// concrete decorator 2
class ElectricShed extends ShedDecorator {
    public ElectricShed(Shed shed) {
        super(shed);
    }

    @Override
    public void build() {
        super.build();
        System.out.println( "Installing electricity" );
    }
}

