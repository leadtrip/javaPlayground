package wood.mike.design.behavioral.state.coffeemachine;

public final class OutOfServiceState extends MachineState {
    public OutOfServiceState(CoffeeMachine machine) { super(machine); }

    @Override
    public void clearError() {
        System.out.println("Refilling supplies... Machine ready.");
        machine.replenishSupplies();
        machine.setState(new IdleState(machine));
    }
}
