package wood.mike.reactive;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;

public class ReactorEmpty {

    public static void main(String[] args) {
        ReactorEmpty re = new ReactorEmpty();
        re.runAll();
    }

    private void runAll() {
        defaultIfEmpty();
        switchIfEmpty();
    }

    private void defaultIfEmpty() {
        Flux<String> emptyStringFlux = Flux.empty();
        var val = emptyStringFlux.defaultIfEmpty("Hello mum").blockLast();
        System.out.println(val);

        String nullString = null;
        val = Mono.justOrEmpty(nullString).defaultIfEmpty("Sagittarius").block();
        System.out.println(val);
    }

    private void switchIfEmpty() {
        String nullString = null;
        var val =  Mono.justOrEmpty(nullString).switchIfEmpty(Mono.just("Bacon")).block();
        System.out.println(val);

        Set<String> roles = new HashSet<>();

        String res = Mono.justOrEmpty( roles.isEmpty() ? null : roles )
                .flatMap( roleIds -> {
                    return Mono.just("Not empty");
                })
                .switchIfEmpty(Mono.defer(() -> {
                    return Mono.just("Empty");
                }))
                .block();

        System.out.println(res);
    }
}
