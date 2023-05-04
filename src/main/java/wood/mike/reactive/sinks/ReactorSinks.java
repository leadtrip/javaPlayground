package wood.mike.reactive.sinks;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.Duration;

import static reactor.core.publisher.Sinks.EmitFailureHandler.FAIL_FAST;

/**
 * In Reactor a sink is a class that allows safe manual triggering of signals in a standalone fashion,
 * creating a Publisher-like structure capable of dealing with multiple Subscriber.
 */
public class ReactorSinks {
    public static void main(String[] args) {
        new ReactorSinks().runAll();
    }

    private void runAll() {
        manyMulticast();
        manyReplayAll();
        manyReplayLatest();
        one();
    }

    /**
     * New subscribers always get the element
     */
    private void one() {
        Sinks.One<Integer> oneSource = Sinks.one();

        Mono<String> hexMono = oneSource.asMono()
                .map(Integer::toHexString);

        hexMono.subscribe(h -> System.out.println("Sinks one subscriber 1 - value as hex: " + h));

        oneSource.emitValue(255, FAIL_FAST);

        hexMono.subscribe(h -> System.out.println("Sinks one subscriber 2 - value as hex: " + h));

        System.out.println();
    }

    /**
     * New subscribers only get elements emitted after they subscribe
     */
    private void manyMulticast() {
        Sinks.Many<String> hotSource = Sinks.unsafe().many().multicast().directBestEffort();
        Flux<String> hotFlux = hotSource.asFlux().map(String::toUpperCase);

        hotFlux.subscribe(d -> System.out.printf("Sinks many multicast subscriber 1 got %s%n", d));

        hotSource.emitNext("blue", FAIL_FAST);
        hotSource.tryEmitNext("green").orThrow();

        hotFlux.subscribe(d -> System.out.printf("Sinks many multicast subscriber 2 got %s%n", d));

        hotSource.emitNext("orange", FAIL_FAST);
        hotSource.emitNext("purple", FAIL_FAST);
        hotSource.emitComplete(FAIL_FAST);

        System.out.println();
    }

    /**
     * Replays all elements to new subscribers
     */
    private void manyReplayAll() {
        Sinks.Many<Integer> replaySink = Sinks.many().replay().all();
        Flux<Integer> replayFlux = replaySink.asFlux();

        replaySink.emitNext(1, FAIL_FAST);
        replayFlux.subscribe(i -> System.out.printf("Sinks many replay all subscriber 1 got %s%n", i));

        replaySink.emitNext(2, FAIL_FAST);
        replayFlux.subscribe(i -> System.out.printf("Sinks many replay all subscriber 2 got %s%n", i));

        System.out.println();
    }

    /**
     * Replays only the latest element to new subscribers
     */
    private void manyReplayLatest() {
        Sinks.Many<Integer> replaySink = Sinks.many().replay().latest();
        Flux<Integer> replayFlux = replaySink.asFlux();

        replaySink.emitNext(1, FAIL_FAST);
        replayFlux.subscribe(i -> System.out.printf("Sinks many replay latest subscriber 1 got %s%n", i));

        replaySink.emitNext(2, FAIL_FAST);
        replayFlux.subscribe(i -> System.out.printf("Sinks many replay latest subscriber 2 got %s%n", i));

        System.out.println();
    }
}
