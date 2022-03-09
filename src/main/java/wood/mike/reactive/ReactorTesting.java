package wood.mike.reactive;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import wood.mike.helper.Person;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;

public class ReactorTesting {

    private static final List<String> WORDS = List.of( "the", "total", "number", "of", "stars", "in", "the", "universe", "is", "greater", "than", "all", "the", "grains", "of", "sand", "on", "all", "the", "beaches", "of", "the", "planet", "Earth" );

    public static void main(String[] args) throws InterruptedException {
        ReactorTesting rt = new ReactorTesting();
        rt.fluxCreation();
        rt.withDelay();
        rt.monoCreation();
    }

    /**
     * All of these examples use push rather than pull because the data is readily available in memory
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

    private void monoCreation() {
        Mono.firstWithValue(
                Mono.just(1).map(integer -> "foo" + integer),
                Mono.delay(Duration.ofMillis(100)).thenReturn("bar")
                )
                .subscribe(System.out::println);

    }
}
