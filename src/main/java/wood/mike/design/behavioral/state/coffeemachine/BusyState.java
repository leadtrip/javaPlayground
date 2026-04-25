package wood.mike.design.behavioral.state.coffeemachine;

public final class BusyState extends MachineState {
    public BusyState(CoffeeMachine machine) { super(machine); }

    public void brew() {
        System.out.println("-> Grinding beans...");
        System.out.println("-> Pouring water...");
        System.out.println("-> Adding milk...");

        machine.reduceSupplies();
        System.out.println("Enjoy your coffee!");

        machine.setState(new IdleState(machine));
    }
}
