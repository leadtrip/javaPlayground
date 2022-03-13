package wood.mike.reactive;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import wood.mike.helper.Person;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Reactive programming is an asynchronous programming paradigm concerned with data streams and the propagation of change.
 * This means that it becomes possible to express static (e.g. arrays) or dynamic (e.g. event emitters) data streams with
 * ease via the employed programming language(s)
 *
 * Reactive streams are push based.
 *
 * You can think of data processed by a reactive application as moving through an assembly line.
 * Reactor is both the conveyor belt and the workstations. The raw material pours from a source (the original Publisher) and ends up as a finished product ready to be pushed to the consumer (or Subscriber).
 *
 * The raw material can go through various transformations and other intermediary steps or be part of a larger assembly
 * line that aggregates intermediate pieces together.
 * If there is a glitch or clogging at one point (perhaps boxing the products takes a disproportionately long time),
 * the afflicted workstation can signal upstream to limit the flow of raw material
 *
 * Nothing Happens Until You subscribe()
 * In Reactor, when you write a Publisher chain, data does not start pumping into it by default. Instead, you create an abstract description of your asynchronous process (which can help with reusability and composition).
 *
 * By the act of subscribing, you tie the Publisher to a Subscriber, which triggers the flow of data in the whole chain. T
 * his is achieved internally by a single request signal from the Subscriber that is propagated upstream, all the way back to the source Publisher.
 */
@Slf4j
public class ReactorTesting {

    private static final List<String> WORDS = List.of( "the", "total", "number", "of", "stars", "in", "the", "universe", "is", "greater", "than", "all", "the", "grains", "of", "sand", "on", "all", "the", "beaches", "of", "the", "planet", "Earth" );

    public static void main(String[] args) throws InterruptedException {
        ReactorTesting rt = new ReactorTesting();
/*        rt.fluxCreation();
        rt.withDelay();
        rt.monoCreation();

        rt.subscribeWithErrorHandler();
        rt.subscribeWithErrorAndCompletionHandling();
        rt.publishAndConsume();
        rt.threadTest();*/
        rt.threadTestStream();
    }

    /**
     * All of these examples use pull rather than push because the data is readily available in memory
     */
    private void fluxCreation() {
        Flux<String> listFlux = Flux.fromIterable(WORDS);
        listFlux.filter(s -> s.length() > 3)
                .sort()
                .subscribe(System.out::println);

        listFlux.filter(s -> s.length() < 3)
                .distinct()
                .sort()
                .subscribe(System.out::println);

        Flux<String> arrayFlux = Flux.fromArray(WORDS.toArray(new String[0]));
        arrayFlux
                .take(3)
                .map(String::toUpperCase)
                .subscribe(System.out::println);

        Flux<Integer> varargsFlux = Flux.just(1, 2, 3);
        varargsFlux
                .sort(Comparator.reverseOrder())
                .subscribe(System.out::println);


        Flux<Person> just1Flux = Flux.just(new Person.PersonBuilder("Carl", "Sagan").dob("1934-11-09").build());
        just1Flux.map(Person::getDob)
                .subscribe(System.out::println);
    }

    /**
     * Because we're running everything in the main thread anything that's truly async isn't seen without pausing somehow
     */
    private void withDelay() throws InterruptedException {
        Flux.just(1, 2, 3)
                .sort(Comparator.reverseOrder())
                .delayElements(Duration.of(200, ChronoUnit.MILLIS))
                .subscribe(System.out::println);

        Mono.just("Hello")
                .concatWith(Mono.just("world")
                .delaySubscription(Duration.ofSeconds(500)))
                .subscribe(System.out::println);

        Thread.sleep(1500);
    }

    public Flux<Integer> publishAndConsume() throws InterruptedException {
        List<Integer> integerList = new ArrayList<>();

        Consumer<String> stringConsumer = s -> {
            System.out.printf("%s Adding %s length to list%n", LocalTime.now(), s );
            integerList.add( s.length() );
        };

        Subscriber<String> stringSubscriber = new Subscriber<>() {
            Subscription subscription;

            @Override
            public void onSubscribe(Subscription subscription) {
                System.out.println("Subscribing");
                this.subscription = subscription;
                subscription.request(1);
            }

            @Override
            public void onNext(String s) {
                subscription.request(1);
                System.out.printf("%s Adding %s length to list%n", LocalTime.now(), s);
                integerList.add(s.length());
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onComplete() {
                System.out.println("Complete");
            }
        };

        Flux<String> listFlux = Flux.fromIterable(WORDS);
        listFlux.filter(s -> s.length() > 3)
                .delayElements(Duration.of(500, ChronoUnit.MILLIS))
                .sort()
                .subscribe(stringSubscriber);


        return Flux.fromIterable( integerList );
    }

    private void monoCreation() {
        Mono.firstWithValue(
                Mono.just(1).map(integer -> "foo" + integer),
                Mono.delay(Duration.ofMillis(100)).thenReturn("bar")
                )
                .subscribe(System.out::println);
    }

    private void subscribeWithErrorHandler() {
        Flux<Integer> ints = Flux.range(1, 4)
                .map(i -> {
                    if (i <= 3) return i;
                    throw new RuntimeException("Got to 4");
                });
        ints.subscribe(System.out::println,                         // standard consumer
                error -> System.err.println("Error: " + error));    // error consumer
    }

    private void subscribeWithErrorAndCompletionHandling() {
        Flux<Integer> ints = Flux.range(1, 4);
        ints.subscribe(System.out::println,
                error -> System.err.println("Error " + error),
                () -> System.out.println("Done"));
    }

    /**
     * Playing around with trying to generate continuous stream of data, reactor doesn't like list being modified
     * understandable but useful investigation
     */
    private void threadTest() {
        List<Integer> integerList = Collections.synchronizedList(new ArrayList<>());
        integerList.add(123);
        integerList.add(4);
        integerList.add(5);
        integerList.add(31);
        integerList.add(5321);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                integerList.add( new Random().nextInt( 100 ) );
                System.out.println(integerList);
            }
        }, 0, 1000);

        Subscriber<Integer> subscriber = new Subscriber<>() {
            Subscription subscription;

            @Override
            public void onSubscribe(Subscription subscription) {
                System.out.println("Subscribing");
                this.subscription = subscription;
                subscription.request(1);
            }

            @Override
            public void onNext(Integer integer) {
                subscription.request(1);
                System.out.printf("%s Got %s %n", LocalTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)), integer);
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onComplete() {
                System.out.println("Complete");
            }
        };

        Flux<Integer> integerFlux = Flux.fromIterable(integerList);
        integerFlux
                .delayElements(Duration.ofSeconds(2))
                .subscribe( subscriber );
    }

    /**
     * More playing around with continuous streams this time making use of Stream API offered by Java
      */
    private void threadTestStream() {
        Supplier<UUID> randomUUIDSupplier = UUID::randomUUID;   // generate random UUID

        Stream<UUID> infiniteStreamOfRandomUUID =               // continuous stream, limited to 10 items
                Stream.generate(randomUUIDSupplier)
                        .limit(10);

        AtomicBoolean done = new AtomicBoolean(false);  // something to control program ending

        Flux<UUID> uuidFlux = Flux.fromStream(infiniteStreamOfRandomUUID).cache();  // Turn this Flux into a hot source and cache last emitted signals for further Subscriber

        uuidFlux.delayElements(Duration.ofMillis(500))      // first subscriber pauses and prints value
                .subscribe(System.out::println);

        Runnable stop = () -> done.set(true);
        Consumer<NumberFormatException> stopConsumer = (nfe) -> done.set(true);

        uuidFlux.delaySubscription(Duration.ofSeconds(3))           // second subscriber, delay subscription for 3 seconds
                .delayElements(Duration.ofMillis(500))          // delay each element
                .doOnComplete( stop )                       // when done fire runnable to end program
                .doOnError( NumberFormatException.class,  stopConsumer )    // same as above if we get a NFE
                .map(uuid -> Long.valueOf(uuid.toString().substring(0, 8), 16))     // convert first 8 chars from hex to long
                .subscribe(uuid ->  System.out.println("Hello " + uuid),            // standard consumer
                        (throwable) -> System.out.println( "Something went wrong " + throwable.getMessage() )); // error consumer

        while (!done.get()) {}      // pause program till released above
    }

}
