package wood.mike.reactive.threading;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * Obtaining a Flux or a Mono does not necessarily mean that it runs in a dedicated Thread.
 * Instead, most operators continue working in the Thread on which the previous operator executed.
 * Unless specified, the topmost operator (the source) itself runs on the Thread in which the subscribe() call was made.
 *
 * In Reactor, the execution model and where the execution happens is determined by the Scheduler that is used.
 * A Scheduler has scheduling responsibilities similar to an ExecutorService.
 *
 * Reactor offers two means of switching the execution context (or Scheduler) in a reactive chain: publishOn and subscribeOn.
 * Both take a Scheduler and let you switch the execution context to that scheduler.
 * But the placement of publishOn in the chain matters, while the placement of subscribeOn does not
 */
public class ReactorThreadingAndSchedulers {

    public static void main(String[] args) throws Exception{
        new ReactorThreadingAndSchedulers().runAll();
    }

    private void runAll() throws Exception{
        noThreading();
        subscribeOn();
        publishOn();
        subscribeAndPublishOn();
    }

    /**
     * Note the thread in the output is always main
     */
    private void noThreading() {
        System.out.println("***** noThreading *****");
        Flux.just("New York", "London", "Paris", "Amsterdam")
                .map(String::toUpperCase)
                .filter(cityName -> cityName.length() <= 8)
                .map(cityName -> cityName.concat(" City"))
                .map(this::printIt)
                .blockLast();
    }

    /**
     * subscribeOn applies to the subscription process, when that backward chain is constructed.
     * As a consequence, no matter where you place the subscribeOn in the chain, it always affects the context of the source emission
     */
    private void subscribeOn() {
        System.out.println("***** subscribeOn *****");
        Flux.just("New York", "London", "Paris", "Amsterdam")
                .map(String::toUpperCase)
                .filter(cityName -> cityName.length() <= 8)
                .map(cityName -> cityName.concat(" City"))
                .subscribeOn(Schedulers.boundedElastic())           // it doesn't matter where this goes in the chain, it'll influence everything from the start
                .map(this::printIt)
                .blockLast();
    }

    /**
     * publishOn applies in the same way as any other operator, in the middle of the subscriber chain.
     * It takes signals from upstream and replays them downstream while executing the callback on a worker from the associated Scheduler.
     * Consequently, it affects where the subsequent operators execute (until another publishOn is chained in)
     */
    private void publishOn() {
        System.out.println("***** publishOn *****");
        Flux.just("New York", "London", "Paris", "Amsterdam")
                .map(this::printIt)                                     // likely done on the main thread
                .publishOn(Schedulers.boundedElastic())
                .map(this::printIt)                                     // now done on a boundedElastic thread
                .blockLast();
    }

    private String printIt(String thing) {
        System.out.println(Thread.currentThread().getName() + " " + thing);
        return thing;
    }

    /**
     * Here we're using a combination of subscribeOn and publishOn.
     * Everything done prior to the publishOn will be done on the subscribeOn thread, then on the publishOn
     */
    private void subscribeAndPublishOn() throws InterruptedException{
        System.out.println("***** subscribeAndPublishOn *****");
                Flux.just("a", "b", "c")
                .doOnNext(v -> printIt("before publishOn " + v))      // this is influenced by subscribeOn
                .publishOn(Schedulers.boundedElastic())                     // the rest is influenced by publishOn
                .doOnNext(v -> printIt("after publishOn " + v))
                .subscribeOn(Schedulers.parallel())
                .subscribe(v -> printIt("received " + v));

        Thread.sleep(1000);
    }
}
