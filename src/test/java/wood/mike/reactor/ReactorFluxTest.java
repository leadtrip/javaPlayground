package wood.mike.reactor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.function.Function;

@Slf4j
public class ReactorFluxTest {

    private final Random random = new Random();

    private static final List<String> JUPITER_MOONS = List.of(
            "Adrastea", "Aitne","Amalthea ","Ananke ","Aoede ", "Arche ","Autonoe ","Callirrhoe ","Callisto ","Carme","Carpo","Chaldene ","Cyllene","Dia","Eirene","Elara ","Erinome ","Ersa","Euanthe ",
            "Eukelade ","Eupheme","Euporie","Europa ","Eurydome","Ganymede","Harpalyke ","Hegemone ","Helike ","Hermippe ","Herse ","Himalia","Io","Iocaste","Isonoe","Kale ","Kallichore","Kalyke",
            "Kore","Leda ","Lysithea ","Megaclite ","Metis","Mneme","Orthosie","Pandia","Pasiphae ","Pasithee","Philophrosyne ","Praxidike ","Sinope","Sponde ","Taygete","Thebe ","Thelxinoe ","Themisto ",
            "Thyone","Valetudo"
    );

    @Test
    public void fluxSubscriber() {
        Flux<String> jupiterMoons = Flux.fromIterable(JUPITER_MOONS)
                .log();

        StepVerifier.create(jupiterMoons)
                .expectNext( JUPITER_MOONS.toArray(new String[0]) )
                .verifyComplete();
    }

    @Test
    public void fluxSubscriberNumbers() {
        Flux<Integer> fluxRange = Flux.range(1, 5)
                .log();

        fluxRange.subscribe(i -> log.info("Number {}", i));

        log.info("-----------------------------------");
        StepVerifier.create(fluxRange)
                .expectNext(1, 2, 3, 4, 5)
                .verifyComplete();
    }

    @Test
    public void fluxSubscriberFromList() {
        Flux<Integer> flux = Flux.fromIterable(List.of(1,2,3,4,5))
                .log();

        flux.subscribe(i -> log.info("Number {}", i));

        log.info("-----------------------------------");
        StepVerifier.create(flux)
                .expectNext(1, 2, 3, 4, 5)
                .verifyComplete();
    }

    @Test
    public void fluxSubscriberNumbersError() {
        Flux<Integer> flux = Flux.range(1,5)
                .log()
                .map(i -> {
                    if(i == 4) {
                        throw new UnsupportedOperationException("Not today");
                    }
                    return i;
                });

        flux.subscribe(i -> log.info("Number {}", i), Throwable::printStackTrace,
                () -> log.info("DONE!"), subscription -> subscription.request(3));

        log.info("-----------------------------------");

        StepVerifier.create(flux)
                .expectNext(1, 2, 3)
                .expectError(UnsupportedOperationException.class)
                .verify();
    }

    @Test
    public void fluxManualSubscriber() throws Exception {
        Subscriber<String> subscriber = new Subscriber<>() {
            volatile Subscription subscription;

            @Override
            public void onSubscribe(Subscription s) {
                subscription = s;
                log.info("initial request for 1 element");
                subscription.request(1);
            }

            public void onNext(String s) {
                log.info("onNext: {}", s);
                log.info("requesting 1 more element");
                subscription.request(1);
            }

            public void onComplete() {
                log.info("onComplete");
            }

            public void onError(Throwable t) {
            }
        };

        Flux<String> stream = Flux.fromIterable( JUPITER_MOONS );
        stream.subscribe(subscriber);

        Thread.sleep(100);
    }

    @Test
    public void fluxBaseSubscriberManuallyHandlingBackPressure() {
        Flux<String> flux = Flux.fromIterable( JUPITER_MOONS )
                .log();

        flux.subscribe(new BaseSubscriber<>() {
            private int count = 0;
            private final int requestCount = 2;

            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                request(requestCount);
            }

            @Override
            protected void hookOnNext(String value) {
                count++;
                if (count >= requestCount) {
                    count = 0;
                    request(requestCount);
                }
            }
        });

        log.info("-----------------------------------");

        StepVerifier.create(flux)
                .expectNext( JUPITER_MOONS.toArray(new String[0]) )
                .verifyComplete();
    }

    @Test
    public void fluxSubscriberPrettyBackpressure() {
        Flux<Integer> flux = Flux.range(1, 10)
                .log()
                .limitRate(2);

        flux.subscribe(i -> log.info("Number {}", i));

        log.info("-----------------------------------");
        StepVerifier.create(flux)
                .expectNext(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .verifyComplete();
    }

    /**
     * Interval creates a Flux that emits long values starting with 0 and incrementing at specified time intervals on the global timer.
     */
    @Test
    public void fluxSubscriberIntervalOne() throws Exception {
        Flux<Long> interval = Flux.interval(Duration.ofMillis(100))
                .take(10)
                .log();

        interval.subscribe(i -> log.info("Number {}", i));

        Thread.sleep(3000);
    }

    /**
     * WithVirtualTime offers the ability to test against a virtual clock, useful when dealing with large time periods
     */
    @Test
    public void fluxSubscriberIntervalDays() {
        StepVerifier.withVirtualTime(this::createInterval)
                .expectSubscription()
                .expectNoEvent(Duration.ofDays(1))
                .thenAwait(Duration.ofDays(1))
                .expectNext(0L)
                .thenAwait(Duration.ofDays(1))
                .expectNext(1L)
                .thenAwait(Duration.ofDays(1))
                .expectNext(2L)
                .thenCancel()
                .verify();
    }

    /**
     * Interval creates a Flux that emits long values starting with 0 and incrementing at specified time intervals on the global timer.
     */
    private Flux<Long> createInterval() {
        return Flux.interval(Duration.ofDays(1))
                .log();
    }

    @Test
    public void connectableFlux() throws Exception {
        ConnectableFlux<Integer> connectableFlux = Flux.range(1, 10)
                .log()
                .delayElements(Duration.ofMillis(100))
                .publish();

//        connectableFlux.connect();

//        log.info("Thread sleeping for 300ms");
//
//        Thread.sleep(300);
//
//        connectableFlux.subscribe(i -> log.info("Sub1 number {}", i));
//
//        log.info("Thread sleeping for 200ms");
//
//        Thread.sleep(200);
//
//        connectableFlux.subscribe(i -> log.info("Sub2 number {}", i));

        StepVerifier
                .create(connectableFlux)
                .then(connectableFlux::connect)
                .thenConsumeWhile(i -> i <= 5)
                .expectNext(6, 7, 8, 9, 10)
                .expectComplete()
                .verify();
    }

    @Test
    public void indexAndTimestampElements() throws InterruptedException {
        Flux.range(2018, 5)
                .timestamp() // Emit a Tuple2 pair of T1 the current clock time in millis and T2 the emitted data (as a T), for each item from this Flux
                .index() // Keep information about the order in which source values were received by indexing them with a 0-based incrementing long, returning a Flux of Tuple2<(index, value)>
                .limitRate(1)
                .delayElements(Duration.ofMillis(200))
                .subscribe(e -> log.info("index: {}, ts: {}, value: {}",
                        e.getT1(),                                      // index
                        Instant.ofEpochMilli(e.getT2().getT1()),        // clock time
                        e.getT2().getT2()));                            // value

        Thread.sleep(1000);
    }

    @Test
    public void reduceExample() {
        Flux.range(1, 5)
                .scan(0, Integer::sum )
                .subscribe(result -> log.info("Result: {}", result));
    }

    @Test
    public void flatmap() {
        Flux.just( "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h" )
            .flatMap(
                    (Function<String, Publisher<?>>) s -> Flux.just(Integer.valueOf(s, 16))
            ).subscribe(
                    System.out::println,
                    (throwable) -> log.error("Oops {}", throwable.getMessage()));

    }

    @Test
    public void sampleExample() throws InterruptedException {
        Flux.range(1, 100)
                .delayElements(Duration.ofMillis(1))
                .sample(Duration.ofMillis(20))
                .subscribe(e -> log.info("onNext: {}", e));

        Thread.sleep(1000);
    }

    @Test
    public void doOnExample() {
        Flux.just(1, 2, 3)
                .concatWith(Flux.error(new RuntimeException("Conn error")))
                .doOnEach(s -> log.info("signal: {}", s))
                .subscribe();
    }

    @Test
    public void handlingErrors() throws InterruptedException {
        Flux.just("user-1")
                .flatMap(user ->
                        recommendedBooks(user)
                                .retryWhen(Retry.backoff( 5, Duration.ofMillis(100) ))
                                .timeout(Duration.ofSeconds(3))
                                .onErrorResume(e -> Flux.just("The Martian"))
                )
                .subscribe(
                        b -> log.info("onNext: {}", b),
                        e -> log.warn("onError: {}", e.getMessage()),
                        () -> log.info("onComplete")
                );

        Thread.sleep(5000);
    }

    public Flux<String> recommendedBooks(String userId) {
        return Flux.defer(() -> {
            if (random.nextInt(10) < 7) {
                return Flux.<String>error(new RuntimeException("Conn error"))
                        .delaySequence(Duration.ofMillis(100));
            } else {
                return Flux.just("Blue Mars", "The Expanse")
                        .delayElements(Duration.ofMillis(50));
            }
        }).doOnSubscribe(s -> log.info("Request for {}", userId));
    }

    @Test
    public void coldPublisher() {
        Flux<String> coldPublisher = Flux.defer(() -> {
            log.info("Generating new items");
            return Flux.just(UUID.randomUUID().toString());
        });

        log.info("No data was generated so far");
        coldPublisher.subscribe(e -> log.info("onNext: {}", e));
        coldPublisher.subscribe(e -> log.info("onNext: {}", e));
        log.info("Data was generated twice for two subscribers");
    }

    @Test
    public void connectExample() {
        Flux<Integer> source = Flux.range(0, 3)
                .doOnSubscribe(s ->
                        log.info("new subscription for the cold publisher"));

        ConnectableFlux<Integer> conn = source.publish();

        conn.subscribe(e -> log.info("[Subscriber 1] onNext: {}", e));
        conn.subscribe(e -> log.info("[Subscriber 2] onNext: {}", e));

        log.info("all subscribers are ready, connecting");
        conn.connect();
    }

    @Test
    public void cachingExample() throws InterruptedException {
        Flux<Integer> source = Flux.range(0, 2)
                .doOnSubscribe(s ->
                        log.info("new subscription for the cold publisher"));

        Flux<Integer> cachedSource = source.cache(Duration.ofSeconds(1));

        cachedSource.subscribe(e -> log.info("[S 1] onNext: {}", e));
        cachedSource.subscribe(e -> log.info("[S 2] onNext: {}", e));

        Thread.sleep(1200);

        cachedSource.subscribe(e -> log.info("[S 3] onNext: {}", e));
    }

    @Test
    public void composeExample() {
        Function<Flux<String>, Flux<String>> logUserInfo = (stream) -> {
            if (random.nextBoolean()) {
                return stream
                        .doOnNext(e -> log.info("[path A] User: {}", e));
            } else {
                return stream
                        .doOnNext(e -> log.info("[path B] User: {}", e));
            }
        };

        Flux<String> publisher = Flux.just("1", "2")
                .transformDeferred(logUserInfo);

        publisher.subscribe();
        publisher.subscribe();
    }

    @Test
    public void connectableFluxAutoConnect() throws Exception {
        Flux<Integer> fluxAutoConnect = Flux.range(1, 5)
                .log()
                .delayElements(Duration.ofMillis(100))
                .publish()
                .autoConnect(2);            // minimum 2 x subscribers


        StepVerifier
                .create(fluxAutoConnect)                // first subscriber
                .then(fluxAutoConnect::subscribe)       // second subscriber
                .expectNext(1,2,3,4,5)
                .expectComplete()
                .verify();
    }
}
