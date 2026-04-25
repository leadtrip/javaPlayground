package wood.mike.design.behavioral.state;

import lombok.Getter;
import lombok.Setter;

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
    @Setter
    @Getter
    RequestState state;
    Long id;

    public Request( Long id ) {
        this.id = id;
        this.state = Received.getInstance();
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

    @Getter
    private static final Received instance = new Received();

    private Received() {}

    @Override
    public void updateState(Request request) {
        System.out.println("Received request");
        request.setState(Validated.getInstance());
    }
}

// -------------------------------------------------

class Validated implements RequestState {

    @Getter
    private static final Validated instance = new Validated();

    private Validated() {}

    @Override
    public void updateState(Request request) {
        System.out.println("Request validated");
        request.setState(SentToHops.getInstance());
    }
}

// -------------------------------------------------

class SentToHops implements RequestState {
    @Getter
    private static final SentToHops instance = new SentToHops();

    private SentToHops() {}

    @Override
    public void updateState(Request request) {
        System.out.println("Request sent to HOPS");
        request.setState(SentToPost.getInstance());
    }
}

// -------------------------------------------------

class SentToPost implements RequestState {
    @Getter
    private static final SentToPost instance = new SentToPost();

    private SentToPost() {}

    @Override
    public void updateState(Request request) {
        System.out.println("Request sent to POST");
        request.setState(Complete.getInstance());
    }
}

// -------------------------------------------------

class Complete implements RequestState {
    @Getter
    private static final Complete instance = new Complete();

    private Complete() {}

    @Override
    public void updateState(Request request) {
        System.out.println("Request complete");
    }
}