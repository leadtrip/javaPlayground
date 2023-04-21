package wood.mike.reactive.errors;

import reactor.core.publisher.Flux;

/**
 * 4.6.1. Error Handling Operators
 * You may be familiar with several ways of dealing with exceptions in a try-catch block. Most notably, these include the following:
 * Catch and return a static default value.
 * Catch and execute an alternative path with a fallback method.
 * Catch and dynamically compute a fallback value.
 * Catch, wrap to a BusinessException, and re-throw.
 * Catch, log an error-specific message, and re-throw.
 * Use the finally block to clean up resources or a Java 7 “try-with-resource” construct.
 */
public class ReactorErrorHandling {

    public static void main(String[] args) {
        ReactorErrorHandling reactorErrorHandling = new ReactorErrorHandling();
        reactorErrorHandling.runAll();
    }

    private void runAll() {
        catchAndReturnDefaultValue();
        catchAndReturnDefaultValueWithPredicate();
        catchAndSwallow();
        fallbackMethod();
        catchAndRethrow();
    }

    private void catchAndReturnDefaultValue() {
        String result = Flux.just(10)
                .map(this::doSomethingDangerous)
                .onErrorReturn("RECOVERED")
                .blockLast();

        System.out.println(result);
    }

    private void catchAndReturnDefaultValueWithPredicate() {
        String result = Flux.just(10)
                .map(this::doSomethingDangerous)
                .onErrorReturn(e -> e.getMessage().equals("This was always going to end badly"), "Foolish")
                .blockLast();

        System.out.println(result);
    }

    private String doSomethingDangerous(Integer integer) throws UnsupportedOperationException{
        throw new UnsupportedOperationException("This was always going to end badly");
    }

    /**
     * Ignore the error and propagate any values up to the error with onErrorComplete which just replaces onError signal with onComplete
     */
    private void catchAndSwallow(){
        Flux.just(10, 20, 30)
                .map(this::doSomethingDangerousOn30)
                .doOnNext(System.out::println)
                .onErrorComplete()
                .blockLast();
    }

    private String doSomethingDangerousOn30(Integer num) {
        if (num == 30) {
            throw new RuntimeException("30 is bad");
        }
        return String.format("%s is safe", num );
    }

    /**
     * If there's a problem with the external service we fall back to a cached value
     */
    private void fallbackMethod() {
        Flux.just("key1", "key2")
            .flatMap(k -> callExternalService(k)
                .onErrorResume(e -> getFromCache(k))
            )
            .doOnNext(System.out::println)
            .blockLast();
    }

    private Flux<String> callExternalService(String key) {
        if (key.equals("key2")) {
            return Flux.error(new RuntimeException());
        }
        return Flux.just(String.format("External service value for %s", key));
    }

    private Flux<String> getFromCache(String key) {
        return Flux.just(String.format("Cached value for %s", key));
    }

    private void catchAndRethrow(){
        Flux.just("key1", "key2")
                .flatMap(this::callExternalService)
                .onErrorMap(original -> new MyException("oops, SLA exceeded", original))
                .blockLast();
    }

    class MyException extends Exception {
        public MyException(String s, Throwable original) {
            System.out.println(s + " " + original.getMessage());
        }
    }
}
