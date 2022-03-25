package wood.mike.design.behavioral.strategy;

/**
 * Strategy pattern is used when we have multiple algorithm for a specific task and client decides the actual implementation to be used at runtime
 */
public class Strategy {
    public static void main(String[] args) {
        Bicycle bicycle = new Bicycle( "Shimano 105", "conti gp5000", "conti gp4000" );
        BikeCheck bikeCheck = new BikeCheck( bicycle );
        bikeCheck.cleanDriveTrain( new OldRag() );
        bikeCheck.pumpFrontTyre( new ElectricPump() );
        bikeCheck.pumpRearTyre( new HandPump() );
    }
}

// ---------------------------------------------------------------------

interface InflateStrategy {
    void inflate( String tyre );
}

class HandPump implements InflateStrategy {
    @Override
    public void inflate( String tyre ) {
        System.out.println( "Inflating, this is going to take a while" );
    }
}

class ElectricPump implements InflateStrategy {
    @Override
    public void inflate(String tyre) {
        System.out.printf("Inflating %s will be done in no time%n", tyre);
    }
}

// ---------------------------------------------------------------------

interface DriveTrainCleaningStrategy {
    void clean( String driveTrain );
}

class OldRag implements DriveTrainCleaningStrategy {
    @Override
    public void clean(String driveTrain) {
        System.out.printf( "Scrubbing %s groupset with old rag%n", driveTrain );
    }
}
// ---------------------------------------------------------------------

/**
 * This class must be fed the strategies for cleaning the drive train and inflating the tyres, they are not stored anywhere
 */
class BikeCheck {

    Bicycle bicycle;

    public BikeCheck(Bicycle bicycle) {
        this.bicycle = bicycle;
    }

    void cleanDriveTrain( DriveTrainCleaningStrategy driveTrainCleaningStrategy ) {
        driveTrainCleaningStrategy.clean( bicycle.driveTrain );
    }

    void pumpFrontTyre( InflateStrategy inflateStrategy ) {
        inflateStrategy.inflate(bicycle.frontTyre);
    }

    void pumpRearTyre( InflateStrategy inflateStrategy ) {
        inflateStrategy.inflate(bicycle.rearTyre);
    }
}

// The class with the things the strategy will be applied to
class Bicycle{
    String driveTrain;
    String frontTyre;
    String rearTyre;

    public Bicycle(String driveTrain, String frontTyre, String rearTyre) {
        this.driveTrain = driveTrain;
        this.frontTyre = frontTyre;
        this.rearTyre = rearTyre;
    }
}