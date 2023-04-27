package wood.mike.reactive.doOn;

import reactor.core.publisher.Flux;

/**
 * The doOnXXXX methods are considered side effects that don't have any effect on the elements
 */
public class ReactorDoOn {

    public static void main(String[] args) {
        new ReactorDoOn().runAll();
    }

    private void runAll() {
        doOnSubscribe();
        doOnNextAndComplete();
        doOnError();
        doOnFinally();
    }

    private void doOnSubscribe() {
        Flux.just("Cheese")
                .doOnSubscribe(subscription -> System.out.println("Subscribed " + subscription))
                .subscribe();
    }


    private void doOnError() {
        Flux.just("Fine", "Okay", "throwException")
                .map(this::badService)
                .doOnNext( s -> System.out.println("Next is " + s))
                .doOnComplete(() -> System.out.println("Complete"))             // this won't happen because of forced error
                .doOnError( e -> System.out.println("Error " + e.getMessage())) // this will happen
                .onErrorComplete()                                              // this is just here to prevent exception propagation to main thread
                .blockLast();
    }

    private void doOnNextAndComplete() {
        Flux.just("One", "Two")
                .doOnNext( s -> System.out.println("Next is " + s))
                .doOnComplete(() -> System.out.println("Complete"))
                .blockLast();
    }

    private void doOnFinally() {
        Flux.just("Orange", "Brown")
                .doFinally(signalType -> System.out.println("Signal " + signalType))
                .subscribe();

        Flux.just("Fine", "Okay", "throwException")
                .map(this::badService)
                .doFinally(signalType -> System.out.println("Signal " + signalType))
                .onErrorComplete()
                .subscribe();
    }

    private String badService( String msg ) {
        if ( msg.equals("throwException") )
            throw new UnsupportedOperationException("Grrr");
        else
            return msg;
    }
}
