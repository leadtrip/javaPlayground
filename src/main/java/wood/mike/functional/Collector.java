package wood.mike.functional;

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
public class Collector {

}
