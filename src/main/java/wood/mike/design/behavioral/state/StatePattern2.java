package wood.mike.design.behavioral.state;

/**
 * State pattern, allows an object to alter its behavior when its internal state changes
 *
 * Example using a request that transitions between internal and external processes
 * received
 * validated
 * sent to hops
 * sent to post
 * complete
 */
public class StatePattern2 {
    public static void main(String[] args) {
        Request request = new Request( 29839210L );
        request.update();
        request.update();
        request.update();
        request.update();
        request.update();
    }
}

// -------------------------------------------------
// the request or context

class Request {
    RequestState state;
    Long id;

    public Request( Long id ) {
        this.id = id;
        this.state = Received.getInstance();
    }

    public RequestState getState() {
        return state;
    }

    public void setState(RequestState state) {
        this.state = state;
    }

    public void update() {
        state.updateState(this );
    }
}

// -------------------------------------------------
// the state which mandates what each state must implement contains reference to context

interface RequestState{
    void updateState(Request request);
}

// -------------------------------------------------

class Received implements RequestState {

    private static final Received instance = new Received();

    private Received() {}

    public static Received getInstance() {
        return instance;
    }

    @Override
    public void updateState(Request request) {
        System.out.println("Received request");
        request.setState(Validated.getInstance());
    }
}

// -------------------------------------------------

class Validated implements RequestState {

    private static final Validated instance = new Validated();

    private Validated() {}

    public static Validated getInstance() {
        return instance;
    }

    @Override
    public void updateState(Request request) {
        System.out.println("Request validated");
        request.setState(SentToHops.getInstance());
    }
}

// -------------------------------------------------

class SentToHops implements RequestState {
    private static final SentToHops instance = new SentToHops();

    private SentToHops() {}

    public static SentToHops getInstance() {
        return instance;
    }

    @Override
    public void updateState(Request request) {
        System.out.println("Request sent to HOPS");
        request.setState(SentToPost.getInstance());
    }
}

// -------------------------------------------------

class SentToPost implements RequestState {
    private static final SentToPost instance = new SentToPost();

    private SentToPost() {}

    public static SentToPost getInstance() {
        return instance;
    }

    @Override
    public void updateState(Request request) {
        System.out.println("Request sent to POST");
        request.setState(Complete.getInstance());
    }
}

// -------------------------------------------------

class Complete implements RequestState {
    private static final Complete instance = new Complete();

    private Complete() {}

    public static Complete getInstance() {
        return instance;
    }

    @Override
    public void updateState(Request request) {
        System.out.println("Request complete");
    }
}