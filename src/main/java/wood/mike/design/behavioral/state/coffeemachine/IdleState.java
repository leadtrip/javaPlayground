package wood.mike.design.behavioral.state.coffeemachine;

public final class IdleState extends MachineState {
    public IdleState(CoffeeMachine machine) { super(machine); }

    @Override
    public void insertCard() {
        if (machine.hasSupplies()) {
            System.out.println("Card accepted. Please select a drink.");
            machine.setState(new AuthorizedState(machine));
        } else {
            System.out.println("Machine empty! Entering maintenance mode.");
            machine.setState(new OutOfServiceState(machine));
        }
    }
}
