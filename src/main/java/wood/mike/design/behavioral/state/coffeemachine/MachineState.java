package wood.mike.design.behavioral.state.coffeemachine;

public sealed abstract class MachineState permits AuthorizedState, BusyState, IdleState, OutOfServiceState {
    protected CoffeeMachine machine;

    public MachineState(CoffeeMachine machine) {
        this.machine = machine;
    }

    public void insertCard() {
        System.out.println("Cannot insert card in this state.");
    }

    public void selectDrink() {
        System.out.println("Selection unavailable.");
    }

    public void clearError() {
        System.out.println("No error to clear.");
    }
}
