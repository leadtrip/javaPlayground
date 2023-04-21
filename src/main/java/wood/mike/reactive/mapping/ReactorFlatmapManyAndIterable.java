package wood.mike.reactive.mapping;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * flatMapMany and flatMapIterable offer the ability to convert a collection to individual elements
 */
@Slf4j
public class ReactorFlatmapManyAndIterable {

    public static void main(String[] args) {
        ReactorFlatmapManyAndIterable reactorFlatmapManyAndIterable = new ReactorFlatmapManyAndIterable();
        reactorFlatmapManyAndIterable.runAll();
    }

    private void runAll() {
        monoTofluxUsingFlatMapMany(monoOfList()).blockLast();
        monoTofluxUsingFlatMapIterable(monoOfList()).blockLast();
    }

    private Flux<String> monoTofluxUsingFlatMapMany(Mono<List<String>> monoList) {
        return monoList
                .flatMapMany(Flux::fromIterable)
                .filter(item -> item.length() > 3)
                .map(item -> "@"+item+"@" )
                .log();
    }

    private Flux<String> monoTofluxUsingFlatMapIterable(Mono<List<String>> monoList) {
        return monoList
                .flatMapIterable(list -> list)
                .filter(item -> item.contains("o"))
                .log();
    }

    private Mono<List<String>> monoOfList() {
        List<String> list = new ArrayList<>();
        list.add("one");
        list.add("two");
        list.add("three");
        list.add("four");

        return Mono.just(list);
    }
}
