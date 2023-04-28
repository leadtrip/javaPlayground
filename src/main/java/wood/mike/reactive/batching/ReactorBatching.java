package wood.mike.reactive.batching;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.math.MathFlux;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ReactorBatching {

    public static void main(String[] args) throws Exception {
        new ReactorBatching().batch();
    }

    private static final Scheduler IO = Schedulers.newParallel("IO");

    public void batch() throws Exception{
        int batchSize = 1000;
        var finalCounts = Flux.fromStream(Files.lines(Paths.get("src/main/resources/names.txt")))
                .subscribeOn(IO)
                // Split to batches
                .buffer(batchSize)
                .doOnNext(__ -> System.out.printf("[%s] batch is provided\n", Thread.currentThread().getName()))
                // Aggregate intermediate counts asynchronously
                .flatMap(ReactorBatching::processBatch)
                .reduce(new HashMap<>(), ReactorBatching::mergeIntermediateCount)
                .flatMapIterable(HashMap::entrySet);

        String mostFrequentName = MathFlux.max(finalCounts, Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .block();


        System.out.printf("The most frequent name is %s\n", mostFrequentName);
    }

    private static HashMap<String, Long> mergeIntermediateCount(HashMap<String, Long> acc,
                                                                Map<String, Long> intermediateResult) {
        intermediateResult.forEach((name, intermediateCount) -> acc.merge(name, intermediateCount, Long::sum));
        return acc;
    }

    private static Mono<Map<String, Long>> processBatch(List<String> batch) {
        return Flux.fromIterable(batch)
                .groupBy(Function.identity())
                .flatMap(group -> group.count().map(count -> Tuples.of(group.key(), count)))
                .collectMap(Tuple2::getT1, Tuple2::getT2)
                .doOnSubscribe(__ -> System.out.printf("[%s] Processing batch... \n", Thread.currentThread().getName()))
                .subscribeOn(Schedulers.boundedElastic());
    }
}
