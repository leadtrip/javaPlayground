package wood.mike.functional;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * A Collector is an interface that describes a mutable reduction operation & is used as the argument to the terminal Stream.collect() method.
 * It tells the stream how to gather the elements into a mutable result container, List, Set...
   The components of a Collector are:
    supplier: a function that returns an empty mutable result container e.g. Arraylist::new
    accumulator: a BiConsumer that incorporates a single element into the container e.g. List::add
    combiner: a BinaryOperator that merges 2 partial result containers into one, used for parallel streams
    finisher: a Function that performs an optional final transformation on the accumulated result container e.g. if you collected StringBuilder but wanted String you'd use toString()
    characteristics: A Set of Collector.Characteristics that provides hints to the stream for optimization e.g. UNORDERED if the order of the elements doesn't matter
   The java.util.stream.Collectors class offers a bunch of static factory methods to do most of what you want a Collector to do.
 */
public class CollectorPlay {

    static final Set<Collector.Characteristics> CH_ID
            = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.IDENTITY_FINISH));

    public static void main(String[] args) {
        CollectorPlay collector = new CollectorPlay();
        collector.sample1();
    }

    record MyCollectorImpl<T, A, R>(Supplier<A> supplier,
                                  BiConsumer<A, T> accumulator,
                                  BinaryOperator<A> combiner,
                                  Function<A, R> finisher,
                                  Set<java.util.stream.Collector.Characteristics> characteristics
    ) implements java.util.stream.Collector<T, A, R> {

        MyCollectorImpl(Supplier<A> supplier,
                      BiConsumer<A, T> accumulator,
                      BinaryOperator<A> combiner,
                      Set<Characteristics> characteristics) {
            this(supplier, accumulator, combiner, castingIdentity(), characteristics);
        }
    }

    @SuppressWarnings("unchecked")
    private static <I, R> Function<I, R> castingIdentity() {
        return i -> (R) i;
    }

    public static <T>Collector<T, ?, List<T>> toList() {
        return new CollectorPlay.MyCollectorImpl<>(
                ArrayList::new,
                List::add,
                (left, right) -> { left.addAll(right); return left; },
                CH_ID);
    }

    private void sample1() {
        Supplier<ArrayList<String>> supplier = ArrayList::new;

        BiConsumer<List<String>, String> accumulator =  (l1, s)  -> {
            l1.add(s);
        };

        BinaryOperator<List<String>> combiner =  (l1, l2) -> {
            l1.addAll(l2);
            return l1;
        };

        MyCollectorImpl myCollector = new MyCollectorImpl(supplier, accumulator, combiner, CH_ID);
    }

}
