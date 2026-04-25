package wood.mike.design.behavioral.state.coffeemachine;

public final class AuthorizedState extends MachineState {

    public AuthorizedState(CoffeeMachine machine) { super(machine); }

    @Override
    public void selectDrink() {
        System.out.println("Drink selected!");
        machine.setState(new BusyState(machine));
        ((BusyState)machine.getState()).brew();
    }
}
