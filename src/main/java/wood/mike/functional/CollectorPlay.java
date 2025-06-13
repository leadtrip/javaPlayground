package wood.mike.functional;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;

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

   In this class we're deconstructing the elements of the Collector to get a feel for what's going on
 */
@Slf4j
public class CollectorPlay {

    static final Set<Collector.Characteristics> CH_ID
            = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.IDENTITY_FINISH));

    public static void main(String[] args) {
        CollectorPlay collectorPlay = new CollectorPlay();
        collectorPlay.test1();
    }

    private void test1() {
        List<String> l1 = Stream.of("1", "2")
                .collect(toList());

        log.info("{}", l1);

        List<String> l2 = Stream.of("1", "2", "3")
                .collect(toStringList());

        log.info("{}", l2);

        List<Integer> l3 = Stream.of("1", "2", "3", "oops")
                .collect(toIntegerList()).reversed();

        log.info("{}", l3);

        List<String> l4 = Stream.of("Apple", "Bread", "Cheese")
                .collect(toIndexedStringList());

        log.info("{}", l4);

        List<String> l5 = Stream.of("Blue", "Octopus", "Recumbent")
                .collect(toStringLengthList());

        log.info("{}", l5);
    }

    /**
     * This is the record copied from the Collectors class, done so to allow easier creation of some Collector's below
     */
    record MyCollectorImpl<T, A, R>(Supplier<A> supplier,
                                  BiConsumer<A, T> accumulator,
                                  BinaryOperator<A> combiner,
                                  Function<A, R> finisher,
                                  Set<Collector.Characteristics> characteristics
    ) implements Collector<T, A, R> {

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

    /**
     * This is the toList Collector taken from the Collectors class
     */
    public static <T>Collector<T, ?, List<T>> toList() {
        return new CollectorPlay.MyCollectorImpl<>(
                ArrayList::new,
                List::add,
                (left, right) -> { left.addAll(right); return left; },
                CH_ID);
    }

    /**
     * Create a basic List<String>
     * Breaking down the elements of the Collector to easily understand
     */
    private static Collector<String, ?, List<String>> toStringList() {
        Supplier<ArrayList<String>> supplier = ArrayList::new;

        BiConsumer<List<String>, String> accumulator =  (l1, s)  -> {
            l1.add(s);
        };

        BinaryOperator<List<String>> combiner =  (l1, l2) -> {
            l1.addAll(l2);
            return l1;
        };

        return new MyCollectorImpl(supplier, accumulator, combiner, CH_ID);
    }

    /**
     * Create a List<Integer> from a stream of String numbers
     * If one of the streamed String elements isn't an Integer it won't be added
     */
    private static Collector<String, ?, List<Integer>> toIntegerList() {
        Supplier<List<Integer>> supplier = ArrayList::new;

        BiConsumer<List<Integer>, String> accumulator =  (l1, s)  -> {
            try{
                l1.add(Integer.parseInt(s));
            }
            catch(NumberFormatException e){
                // ignore
            }
        };

        BinaryOperator<List<Integer>> combiner =  (l1, l2) -> {
            l1.addAll(l2);
            return l1;
        };

        return new MyCollectorImpl(supplier, accumulator, combiner, CH_ID);
    }

    /**
     * Create a List<String> where each String is prefixed with its position in the List
     */
    private static Collector<String, ?, List<String>> toIndexedStringList() {
        Supplier<List<String>> supplier = ArrayList::new;
        BiConsumer<List<String>, String> accumulator =  (l1, s)  -> {
            l1.add(l1.size() + ":" + s);
        };
        BinaryOperator<List<String>> combiner = (l1, l2) -> {
            l1.addAll(l2);
            return l1;
        };

        return new MyCollectorImpl(supplier, accumulator, combiner, CH_ID);
    }

    /**
     * Directly implement a Collector that creates a List<String> where the String is prefixed with the String length
     */
    private static Collector<String, List<String>, List<String>> toStringLengthList() {
        return new Collector<>() {
            @Override
            public Supplier<List<String>> supplier() {
                return ArrayList::new;
            }

            @Override
            public BiConsumer<List<String>, String> accumulator() {
                return (ls, s) -> {
                    ls.add(s.length() + ":" + s);
                };
            }

            @Override
            public BinaryOperator<List<String>> combiner() {
                return (List<String> l1, List<String> l2) -> {
                    l1.addAll(l2);
                    return l1;
                };
            }

            @Override
            public Function<List<String>, List<String>> finisher() {
                return Function.identity();
            }

            @Override
            public Set<Characteristics> characteristics() {
                return Set.of();
            }
        };
    }
}
