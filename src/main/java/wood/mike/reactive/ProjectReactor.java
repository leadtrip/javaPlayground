package wood.mike.reactive;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import wood.mike.helper.Person;

import java.time.Duration;
import java.time.LocalDateTime;
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
 * SEE ALSO ReactorMonoTest and ReactorFluxTest
 *
 * Reactive programming is an asynchronous programming paradigm concerned with data streams and the propagation of change.
 * This means that it becomes possible to express static (e.g. arrays) or dynamic (e.g. event emitters) data streams with
 * ease via the employed programming language(s)
 *
 * Reactive streams are push based.
 *
 * You can think of data processed by a reactive application as moving through an assembly line.
 * Reactor is both the conveyor belt and the workstations.
 * The raw material pours from a source (the original Publisher) and ends up as a finished product ready to be pushed to the consumer (or Subscriber).
 *
 * The raw material can go through various transformations and other intermediary steps or be part of a larger assembly
 * line that aggregates intermediate pieces together.
 * If there is a glitch or clogging at one point (perhaps boxing the products takes a disproportionately long time),
 * the afflicted workstation can signal upstream to limit the flow of raw material
 *
 * Nothing Happens Until You subscribe()
 * In Reactor, when you write a Publisher chain, data does not start pumping into it by default.
 * Instead, you create an abstract description of your asynchronous process (which can help with reusability and composition).
 *
 * By the act of subscribing, you tie the Publisher to a Subscriber, which triggers the flow of data in the whole chain.
 * This is achieved internally by a single request signal from the Subscriber that is propagated upstream, all the way back to the source Publisher.
 */
@Slf4j
public class ProjectReactor {

    private static final List<String> WORDS = List.of( "the", "total", "number", "of", "stars", "in", "the", "universe", "is", "greater", "than", "all", "the", "grains", "of", "sand", "on", "all", "the", "beaches", "of", "the", "planet", "Earth" );

    public static void main(String[] args) throws InterruptedException {
        ProjectReactor rt = new ProjectReactor();
/*        rt.publishers();
        rt.fluxCreation();
        rt.withDelay();
        rt.monoCreation();

        rt.subscribeWithErrorHandler();
        rt.subscribeWithErrorAndCompletionHandling();
        rt.publishAndConsume();
        rt.threadTest();
        rt.threadTestStream();*/
        rt.hotAndColdPublishers();
    }

    // Flux and Mono are both Publishers
    // Flux handles 0..n and Mono 0..1
    private void publishers() {
        Publisher<String> jupiterMoons = Flux.just( "europa", "ganymede", "io" );
        Publisher<String> earthMoons = Mono.just( "moon" );
    }

    private void monoCreation() {
        Mono<String> stream1 = Mono.just("One");
        Mono<String> stream2 = Mono.justOrEmpty(null);
        Mono<String> stream3 = Mono.justOrEmpty(Optional.empty());
        Mono<String> stream4 = Mono.fromCallable(() -> httpRequest());
        Mono<String> stream5 = Mono.fromCallable(this::httpRequest);

        Mono.firstWithValue(
                        Mono.just(1).map(integer -> "foo" + integer),
                        Mono.delay(Duration.ofMillis(100)).thenReturn("bar")
                )
                .subscribe(System.out::println);
    }

    private String httpRequest() {
        log.info("Making HTTP request");
        throw new RuntimeException("IO error");
    }

    /**
     * All of these examples use pull rather than push because the data is readily available in memory
     */
    private void fluxCreation() {
        // iterable
        Flux<String> listFlux = Flux.fromIterable(WORDS);
        listFlux.filter(s -> s.length() > 3)
                .sort()
                .subscribe(System.out::println);

        listFlux.filter(s -> s.length() < 3)
                .distinct()
                .sort()
                .subscribe(System.out::println);

        // array
        Flux<String> arrayFlux = Flux.fromArray(WORDS.toArray(new String[0]));
        arrayFlux
                .take(3)
                .map(String::toUpperCase)
                .subscribe(System.out::println);

        // varargs
        Flux<Integer> varargsFlux = Flux.just(1, 2, 3);
        varargsFlux
                .sort(Comparator.reverseOrder())
                .subscribe(System.out::println);

        // just
        Flux<Person> just1Flux = Flux.just(new Person.PersonBuilder("Carl", "Sagan").dob("1934-11-09").build());
        just1Flux.map(Person::getDob)
                .subscribe(System.out::println);

        // range
        Flux<Integer> rangeFlux =
                Flux.range(1, 500);
        rangeFlux.subscribe(System.out::println);
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
        ints.subscribe(System.out::println,                     // standard consumer
                error -> System.err.println("Error " + error),  // error consumer
                () -> System.out.println("Done"));              // completion consumer
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

    private Stream<String> getMovie( String movie ){
        System.out.printf("Got the movie streaming request for %1s%n", movie);
        return Stream.of(
                "scene 1",
                "scene 2",
                "scene 3",
                "scene 4",
                "scene 5"
        );
    }

    /**
     * There are 2 broad families of publishers, hot and cold.
     * Cold publishers generate data anew for each subscription. If no subscription is created, data never gets generated.
     * Hot publishers, on the other hand, do not depend on any number of subscribers.
     * They might start publishing data right away and would continue doing so whenever a new Subscriber comes in
     * (in which case, the subscriber would see only new elements emitted after it subscribed)
     */
    private void hotAndColdPublishers() throws InterruptedException {
        /*coldPublisher1();
        hotPublisher1();
        hotPublisherRefCount();*/
        hotPublisherJust();
        coldPublisherDefer();
    }

    /**
     * All the data is available to subscribers of a cold publisher no matter when they subscribe.
     * Each subscriber gets a dedicated publisher.
     * As seen below, both Dave and Jane watch all the movie.
     */
    private void coldPublisher1() throws InterruptedException{
        Flux<String> netFlux = Flux.fromStream(() -> getMovie("LOTR"))
                .delayElements(Duration.ofSeconds(1));

        // Dave starts watching the movie
        netFlux.subscribe(scene -> System.out.println("Dave is watching " + scene));

        Thread.sleep(3000);
        // Jane starts watching the movie sometime later but while Dave is still watching, the publisher for Jane is independent of Dave's
        netFlux.subscribe(scene -> System.out.println("Jane is watching " + scene));
        Thread.sleep(6000);
    }

    /**
     * In the case of a hot publisher, data starts to flow when the first subscriber subscribes, and subsequent subscribers
     * pick up the flow of data from when they subscribe only, potentially missing previous data as in the case below
     * where the share method is invoked on the Flux.
     *
     * The use of share below converts the cold publisher into hot.
     */
    private void hotPublisher1() throws InterruptedException {
        Flux<String> movieTheatre = Flux.fromStream(() -> getMovie("The hills have eyes"))
                .delayElements(Duration.ofSeconds(1)).share();

        // Dave starts watching the movie
        movieTheatre.subscribe(scene -> System.out.println("Dave is watching " + scene));

        Thread.sleep(3000);
        // Jane starts watching the movie sometime later but while Dave is still watching, Jane misses the start of the movie
        movieTheatre.subscribe(scene -> System.out.println("Jane is watching " + scene));
        Thread.sleep(6000);
    }

    /**
     * This example uses refCount to specify the minimum number of subscribers that should subscribe before connecting to
     * the upstream source
     */
    private void hotPublisherRefCount() throws InterruptedException{
        Flux<String> movieTheatre = Flux.fromStream(() -> getMovie("Bambi"))
                .delayElements(Duration.ofSeconds(1)).publish().refCount(2);

        // Dave starts watching the movie & nothing happens because we need 2 x subscribers
        movieTheatre.subscribe(scene -> System.out.println("Dave is watching " + scene));

        Thread.sleep(1000);
        // Jane starts watching the movie sometime later, now we have 2 x subscribers & the publisher can publish
        movieTheatre.subscribe(scene -> System.out.println("Jane is watching " + scene));
        Thread.sleep(6000);
    }

    private Mono<String> sampleMsg(String str) {
        System.out.printf("Call to Retrieve Sample Message!! --> %s at: %s%n", str, LocalDateTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM)));
        return Mono.just(str);
    }

    private void hotPublisherJust() throws InterruptedException {
        System.out.println("Starting...");

        // just captures the value at assembly time and replays to subscribers later, no subscribers are required
        Mono<String> justMono = sampleMsg("Lamenting");

        Thread.sleep(1000);

        justMono.subscribe(System.out::println);

        Thread.sleep(1000);

        justMono.subscribe(System.out::println);
    }

    private void coldPublisherDefer() throws InterruptedException {
        System.out.println("Starting...");

        // the use of defer converts publisher to cold and each subscriber results in call to publisher
        Mono<String> justMono = Mono.defer( () -> sampleMsg("Anarchic") );

        Thread.sleep(1000);

        justMono.subscribe(System.out::println);

        Thread.sleep(1000);

        justMono.subscribe(System.out::println);
    }
}
