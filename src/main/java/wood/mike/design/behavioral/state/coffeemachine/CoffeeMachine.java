package wood.mike.design.behavioral.state.coffeemachine;

import lombok.Getter;
import lombok.Setter;

public class CoffeeMachine {
    @Setter
    @Getter
    private MachineState state;
    private int beanLevel = 10;
    private int milkLevel = 10;

    public CoffeeMachine() {
        this.state = new IdleState(this);
    }

    public boolean hasSupplies() {
        return beanLevel > 0 && milkLevel > 0;
    }

    public void reduceSupplies() {
        beanLevel--;
        milkLevel--;
    }

    public void replenishSupplies() {
        beanLevel = 10;
        milkLevel = 10;
    }

    public void cardPresented() { state.insertCard(); }
    public void drinkSelected() { state.selectDrink(); }
    public void clearError() { state.clearError(); }

}