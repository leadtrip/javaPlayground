package wood.mike.design.behavioral.command;

import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulate in an object all the data required for performing a given action (command), including what method to call,
 * the method's arguments, and the object to which the method belongs.
 *
 * CommandPattern is the client class here that controls the command execution process.
 */
public class CommandPattern {
    public static void main(String[] args) {
        Bicycle bicycle = new Bicycle();
        CyclingOperationExecutor executor = new CyclingOperationExecutor();
        executor.performOperation( new PedalOperation( bicycle ) );
        executor.performOperation( new BrakeOperation( bicycle ) );
        executor.performOperation( new PedalOperation( bicycle ) );
        executor.performOperation( new TurnRightOperation( bicycle ) );
        executor.performOperation( () -> System.out.println( "Turning left" ) );    // we can also use lambda expressions which provides ability to do away with concrete command classes completely if wanted
        executor.performOperation( bicycle::brake );                                // and method references
    }
}

// ------------ invoker class --------------------------
// doesn't have to store previous actions but can be useful e.g. for undoing previous actions

class CyclingOperationExecutor {
    private List<CyclingOperation> cyclingOperations = new ArrayList<>();

    public void performOperation( CyclingOperation cyclingOperation ) {
        cyclingOperations.add(cyclingOperation);
        cyclingOperation.perform();
    }
}

// ------------ command classes -----------------------
// the command classes contain all the information required to execute the action including reference to the receiver (Bicycle)

@FunctionalInterface
interface CyclingOperation {
    void perform();
}

// pedal
class PedalOperation implements CyclingOperation {
    private Bicycle bicycle;

    public PedalOperation(Bicycle bicycle) {
        this.bicycle = bicycle;
    }

    @Override
    public void perform() {
        bicycle.pedal();
    }
}

// brake
class BrakeOperation implements CyclingOperation {
    private Bicycle bicycle;

    public BrakeOperation(Bicycle bicycle) {
        this.bicycle = bicycle;
    }

    @Override
    public void perform() {
        bicycle.brake();
    }
}

// turn right
class TurnRightOperation implements CyclingOperation {
    private Bicycle bicycle;

    public TurnRightOperation(Bicycle bicycle) {
        this.bicycle = bicycle;
    }

    @Override
    public void perform() {
        bicycle.turnRight();
    }
}

// ---------- receiver class ---------------------

class Bicycle {
    void pedal(){
        System.out.println("Pedalling");
    }
    void brake(){
        System.out.println("Braking");
    }
    void turnRight(){
        System.out.println("Turning right");
    }
    void turnLeft(){
        System.out.println("Turning left");
    }
}