package wood.mike.design.behavioral.state;

/**
 * State pattern is used when an Object changes its behavior based on its internal state.
 */
public class StatePattern {
    public static void main(String[] args) {
        FanContext fanContext = new FanContext();
        State startFanState = new FanStartState();
        State stopFanState = new FanOffState();

        fanContext.setFanState(startFanState);
        fanContext.perform();

        fanContext.setFanState(stopFanState);
        fanContext.perform();
    }
}

interface State {
    void perform();
}

class FanStartState implements State {
    @Override
    public void perform() {
        System.out.println("Fan on");
    }
}

class FanOffState implements State {
    @Override
    public void perform() {
        System.out.println("Fan off");
    }
}

class FanContext implements State{
    private State fanState;

    public State getFanState() {
        return fanState;
    }

    public void setFanState(State fanState) {
        this.fanState = fanState;
    }

    @Override
    public void perform() {
        fanState.perform();
    }
}