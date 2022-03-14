package wood.mike.reactor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.function.Consumer;

/*
    Testing Reactor Mono
 */
@Slf4j
public class ReactorMonoTest {

        @Test
        public void monoSubscriber() {
            String name = "Lonely";
            Mono<String> mono = Mono.just(name)
                    .log();

            mono.subscribe();
            log.info("--------------------------");
            StepVerifier.create(mono)
                    .expectNext(name)
                    .verifyComplete();
        }

        @Test
        public void monoSubscriberConsumer() {
            String name = "Lonely";
            Mono<String> mono = Mono.just(name)
                    .log();

            mono.subscribe(s -> log.info("Value {}", s));
            log.info("--------------------------");

            StepVerifier.create(mono)
                    .expectNext(name)
                    .verifyComplete();
        }

        @Test
        public void monoSubscriberConsumerError() {
            String name = "Lonely";
            Mono<String> mono = Mono.just(name)
                    .map(s -> {throw new RuntimeException("You know what's coming");});

            mono.subscribe(s -> log.info("Name {}", s), s -> log.error("Told you"));
            mono.subscribe(s -> log.info("Name {}", s), Throwable::printStackTrace);

            log.info("--------------------------");

            StepVerifier.create(mono)
                    .expectError(RuntimeException.class)
                    .verify();
        }
        

    @Test
    public void monoSubscriberConsumerComplete() {
        String name = "Lonely";
        Mono<String> mono = Mono.just(name)
                .log()
                .map(String::toUpperCase);

        mono.subscribe(s -> log.info("Value {}", s),
                Throwable::printStackTrace,
                () -> log.info("FINISHED!"));

        log.info("--------------------------");

        StepVerifier.create(mono)
                .expectNext(name.toUpperCase())
                .verifyComplete();
    }

    @Test
    public void monoSubscriberConsumerSubscription() {
        String name = "Lonely";
        Mono<String> mono = Mono.just(name)
                .log()
                .map(String::toUpperCase);

        mono.subscribe(s -> log.info("Value {}", s),
                Throwable::printStackTrace,
                () -> log.info("FINISHED!")
                , Subscription::cancel);

        log.info("--------------------------");

        StepVerifier.create(mono)
                .expectNext(name.toUpperCase())
                .verifyComplete();
    }

    @Test
    public void monoDoOnMethods() {
        String name = "Lonely";
        Mono<Object> mono = Mono.just(name)
                .log()
                .map(String::toUpperCase)
                .doOnSubscribe(subscription -> log.info("Subscribed"))
                .doOnRequest(longNumber -> log.info("Request Received, starting doing something..."))
                .doOnNext(s -> log.info("Value is here. Executing doOnNext {}",s))
                .flatMap(s -> Mono.empty())
                .doOnNext(s -> log.info("Value is here. Executing doOnNext {}",s)) //will not be executed
                .doOnSuccess(s -> log.info("doOnSuccess executed {}", s));

        mono.subscribe(s -> log.info("Value {}", s),
                Throwable::printStackTrace,
                () -> log.info("FINISHED!"));

        log.info("--------------------------");
    }

    Consumer<Throwable> errorConsumer = (ex) -> log.error("Error message: {}", ex.getMessage());

    @Test
    public void monoDoOnError() {
        Mono<Object> error = Mono.error(new IllegalArgumentException("Illegal argument exception"))
                .doOnError(errorConsumer)
                .doOnNext(s -> log.info("Executing this doOnNext"))
                .log();

        StepVerifier.create(error)
                .expectError(IllegalArgumentException.class)
                .verify();
    }

    @Test
    public void monoOnErrorResume() {
        String name = "Lonely";
        Mono<Object> error = Mono.error(new IllegalArgumentException("Illegal argument exception"))
                .onErrorResume(s -> {
                    log.info("Inside On Error Resume");
                    return Mono.just(name);
                })
                .doOnError(errorConsumer)
                .log();

        StepVerifier.create(error)
                .expectNext(name)
                .verifyComplete();
    }

    @Test
    public void monoOnErrorReturn() {
        String name = "Lonely";
        Mono<Object> error = Mono.error(new IllegalArgumentException("Illegal argument exception"))
                .onErrorReturn("EMPTY")
                .onErrorResume(s -> {
                    log.info("Inside On Error Resume");
                    return Mono.just(name);
                })
                .doOnError(errorConsumer)
                .log();

        StepVerifier.create(error)
                .expectNext("EMPTY")
                .verifyComplete();
    }
}
