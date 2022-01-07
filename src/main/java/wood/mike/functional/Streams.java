package wood.mike.functional;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.*;

/**
 * Stuff you can do with Java Streams
 */
public class Streams {

    static final List<String> friends = Arrays.asList("Brian", "Nate", "Neal", "Raju", "Sara", "Scott");

    public static void main(String[] args) {
        creatingStreams();
        filter();
        map();
        mapTo();
        flatMap();
        flatMapTo();
        distinct();
        sorted();
        peek();
        limit();
        skip();
        takeWhile();
        dropWhile();
        forEach();
        forEachOrdered();
        toArray();
        reduce();
        collect();
        toList();
        min();
        max();
        count();
        anyMatch();
        allMatch();
        noneMatch();
        findFirst();
        findAny();
    }

    /**
     * Find any element in stream
     */
    private static void findAny() {
        System.out.println("\n+findAny+\n");

        String any = friends.stream().findAny().get();
        System.out.println(any); // ?

        System.out.println("================================================");
    }

    /**
     * Find first element in stream
     */
    private static void findFirst() {
        System.out.println("\n+findFirst+\n");

        String first = friends.stream().findFirst().get();
        System.out.println(first); // Brian

        System.out.println("================================================");
    }

    /**
     * true if all elements don't match predicate
     */
    private static void noneMatch() {
        System.out.println("\n+noneMatch+\n");

        boolean allEndWithPercent =
                friends.stream()
                    .noneMatch(s -> s.endsWith("%".toUpperCase()));
        System.out.println( allEndWithPercent ); // true

        System.out.println("================================================");
    }

    /**
     * true if all elements match predicate
     */
    private static void allMatch() {
        System.out.println("\n+allMatch+\n");

        boolean allGtTwo =
                friends.stream()
                        .allMatch(s -> s.length() > 2);

        System.out.println(allGtTwo); // true

        System.out.println("================================================");
    }

    /**
     * true if any elements matches predicate
     */
    private static void anyMatch() {
        System.out.println("\n+anyMatch+\n");

        boolean anyNs = friends.stream()
                    .anyMatch(s -> s.startsWith("N"));
        System.out.println(anyNs); // true

        System.out.println("================================================");
    }

    /**
     * Count elements in stream
     */
    private static void count() {
        System.out.println("\n+count+\n");

        long cnt = friends.stream().count();
        System.out.println(cnt); // 6

        System.out.println("================================================");
    }

    /**
     * Get max value
     */
    private static void max() {
        System.out.println("\n+max+\n");

        // get max based on standard ordering
        Integer max = Stream.of(9,3,4,5,2).max(Integer::compare).get();
        System.out.println(max); // 9

        System.out.println("================================================");
    }

    /**
     * Get min value
     */
    private static void min() {
        System.out.println("\n+min+\n");

        // get min based on standard ordering
        Integer min = Stream.of(9,3,4,5,2).min(Integer::compare).get();
        System.out.println(min); // 2

        // apply different ordering criteria
        Optional<String> minLenFriend =
                Stream.of("one", "three", "ninety nine", "ea", "0xbbee")
                    .min(Comparator.comparingInt(str -> str.charAt(str.length() - 1)));
        System.out.println(minLenFriend.get()); // ea

        System.out.println("================================================");
    }

    /**
     * Accumulates the elements of this stream into a Lis
     */
    private static void toList() {
        System.out.println("\n++\n");

        List<Integer> lens =
            friends.stream()
                .map(String::length)
                .toList();

        System.out.println("================================================");
    }


    private static void collect() {
        System.out.println("\n+collect non-parallel combiner not used+\n");

        List<String> vowels = List.of("a", "e", "i", "o", "u");

        // sequential stream - nothing to combine
        StringBuilder result = vowels.stream()
                .collect(
                        StringBuilder::new,                     // identity
                        (x, y) -> x.append(y),                  // accumulator
                        (a, b) -> a.append(",").append(b));     // combiner - not used here as elements are processed sequentially so no need to combine
        System.out.println(result.toString());  // aeiou

        System.out.println("\n+collect parallel combiner is used+\n");

        // parallel stream - combiner is combining partial results
        StringBuilder result1 = vowels.parallelStream()
                .collect(
                        StringBuilder::new,                     // identity
                        (x, y) -> x.append(y),                  // accumulator
                        (a, b) -> a.append(",").append(b));     // combiner - is used here because parallel streams used so combination required as final step
        System.out.println(result1.toString()); // a,e,i,o,u

        System.out.println("\n+collect Collectors.toList()+\n");

        List<Integer> evenNumbers =
                Stream.of(1, 2, 3, 4, 5, 6)
                        .filter(x -> x % 2 == 0)
                        .collect(Collectors.toList());
        System.out.println(evenNumbers);  // [2, 4, 6]

        System.out.println("\n+collect Collectors.toSet()+\n");

        Set<Integer> oddNumbers =
                List.of(1, 2, 3, 4, 5, 6)
                        .parallelStream()
                        .filter(x -> x % 2 != 0).collect(Collectors.toSet());
        System.out.println(oddNumbers); // [1, 3, 5]

        System.out.println("\n+collect Collectors.toMap()+\n");

        Map<Integer, String> mapOddNumbers =
                List.of(1, 2, 3, 4, 5, 6)
                        .parallelStream().filter(x -> x % 2 != 0)
                        .collect(Collectors.toMap(Function.identity(), x -> String.valueOf(x)));
        System.out.println(mapOddNumbers); // {1=1, 3=3, 5=5}

        System.out.println("\n+collect Collectors.joining()+\n");

        String valueCSV =
                Stream.of("a", "b", "c")
                        .collect(Collectors.joining(","));
        // a,b,c

        System.out.println("================================================");
    }

    /**
     * combine elements of a stream and produce a single value.
     * There are 2 or 3 parts to the reduction process, for non-parallel and parallel the following 2 are always required...
        * identity - The identity element is both the initial value of the reduction and the default result if there are no elements in the stream
        * accumulator - The accumulator function takes two parameters: a partial result of the reduction and the next element of the stream. It returns a new partial result
     * And an additional step is required where the final result type is different from the accumulated type.
        * combiner - combines the intermediate values into a single result. If we aren't switching types, it turns out that the accumulator function is the same as the combiner function
     */
    private static void reduce() {
        System.out.println("\n+reduce+\n");

        // long way of doing it but clearly identifying the parts required
        // starting and ending type of string means no combiner is required
        String identity = "";
        BinaryOperator<String> accumulator = (partialRes, current) -> partialRes + current;

        List<String> letters = Arrays.asList("a", "b", "c", "d", "e");
        String result = letters
                .stream()
                .reduce( identity, accumulator );
        // result == abcde

        // above can be condensed down to...
        String res = letters.stream().reduce("", String::concat);


        // this time using integer as starting and ending type means no combiner required again
        List<Integer> ages = Arrays.asList(25, 30, 45, 28, 32);
        int computedAges = ages.parallelStream().reduce(
                0,  // identity
                Integer::sum);  // accumulator

        System.out.println("computedAges: " + computedAges);

        // now we're starting with string and ending with integer so we need the combiner
        int length = Arrays.asList("one", "two","three","four")
                .parallelStream()
                .reduce(0,                                               // identity
                        (accumulatedInt, str) -> accumulatedInt + str.length(), // accumulator
                        Integer::sum);                                          // combiner

        System.out.println( "length of strings: " + length );

        System.out.println("================================================");
    }

    private static void toArray() {
        System.out.println("\n+toArray+\n");

        Object[] arr1 = friends.stream()
                .map(String::toUpperCase)
                .toArray();

        String[] arr2 = friends.stream()
                .map(String::toUpperCase)
                .toArray(String[]::new);

        System.out.println("================================================");
    }

    /**
     * forEachOrdered seems more useful when processing parallel streams as per the examples below
     * when using parallel with forEach the ouptut is not guaranteed but with forEachOrdered is remains as per the source order
     */
    private static void forEachOrdered() {
        System.out.println("\n+forEachOrdered+\n");

        System.out.println("\n+forEach parallel+\n");
        Stream.of(9,2,4,5,7,1)
            .parallel()
            .forEach(System.out::println);

        System.out.println("\n+forEachOrdered parallel+\n");
        Stream.of(9,2,4,5,7,1)
            .parallel()
            .forEachOrdered(System.out::println);

        System.out.println("================================================");
    }

    private static void forEach() {
        System.out.println("\n+forEach+\n");

        System.out.println("\n+forEach list+\n");
        // forEach on list
        friends.stream()
                .forEach(System.out::println);

        Map<String, Double> map = new HashMap<String, Double>();
        map.put("Forrest Gump", 8.8);
        map.put("The Matrix", 8.7);
        map.put("The Hunt", 8.3);
        map.put("Monty Python's Life of Brian", 8.1);
        map.put("Who's Singin' Over There?", 8.9);

        System.out.println("\n+forEach map stream+\n");

        // forEach on Map - first off streaming and filtering
        map.entrySet()
                .stream()
                .filter(entry -> entry.getValue() > 8.4)
                .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));

        System.out.println("\n+forEach map direct+\n");

        // now doing all the work in forEach
        map.forEach((k, v) -> {
            if (v > 8.4) {
                System.out.println(k + ": " + v);
            }
        });

        System.out.println("================================================");
    }

    /**
     * The opposite of takeWhile, removes the first sequence of elements that match the predicate
     */
    private static void dropWhile() {
        System.out.println("\n+dropWhile+\n");

        Stream.of(2, 4, 6, 8, 9, 10, 12)
                .dropWhile(n -> n % 2 == 0)
                .forEach(System.out::println);  // prints 9 10 12, preceding numbers were dropped as they matched the predicate

        System.out.println("================================================");
    }

    /**
     * takes the first sequence of elements from the initial stream while the predicate holds true.
     * Meaning that when an element is encountered that does not match the predicate, the rest of the stream is discarded
     */
    private static void takeWhile() {
        System.out.println("\n+takeWhile+\n");

        Stream.of(2, 4, 6, 8, 9, 10, 12)
                .takeWhile(n -> n % 2 == 0)
                .forEach(System.out::println);  // prints 2 4 6 8 but not 10 or 12 because 9 breaks the predicate

        System.out.println("================================================");
    }

    /**
     * limit returns a stream not longer than the requested size
     */
    private static void limit(){
        System.out.println("\n+limit+\n");
        friends.stream()
                .limit(2)
                .forEach(System.out::println);
        System.out.println("================================================");
    }

    /**
     * skip the first n elements of the stream
     */
    private static void skip() {
        System.out.println("\n+skip+\n");
        friends.stream()
                .skip(3)
                .forEach(System.out::println);
        System.out.println("================================================");
    }

    /**
     * peek exists mainly to support debugging, where you want to see the elements as they flow past a certain point in a pipeline
     */
    private static void peek() {
        System.out.println("\n+peek+\n");
        friends.stream()
                .filter(e -> e.length() > 4)
                .peek(e -> System.out.println("Filtered value: " + e))
                .map(String::toUpperCase)
                .peek(e -> System.out.println("Mapped value: " + e))
                .collect(Collectors.toList());
        System.out.println("================================================");
    }

    /**
     * Two sorted options, one takes no args and applies natural ordering, the other taking a comparator
     */
    private static void sorted() {
        System.out.println("\n+sorted+\n");
        friends.stream()
                .sorted()
                .forEach(System.out::println);

        System.out.println("================================================");

        System.out.println("\n+reverse sorted+\n");
        friends.stream()
                .sorted(Comparator.reverseOrder())
                .forEach(System.out::println);

        System.out.println("================================================");
    }

    private static void distinct() {
        System.out.println("\n+distinct+\n");
        Stream.of(1,1,3,4,1,2,1,2,3,5)
                .distinct()
                .forEach(System.out::println);

        System.out.println("================================================");
    }

    /**
     * As per the various mapTo.. methods there are corresponding flatMapTo.. methods
     */
    private static void flatMapTo() {
        System.out.println("\n+flatMapTo+\n");
        List<List<String>> listOfLists = Arrays.asList(
                Arrays.asList("1", "2"),
                Arrays.asList("5", "6"),
                Arrays.asList("3", "4")
        );

        IntStream intStream =
                listOfLists.stream()
                        .flatMapToInt(childList ->
                                childList.stream()
                                        .mapToInt(Integer::parseInt));
        //let's peek and find sum of the elements
        int sum = intStream.peek(System.out::println)
                .sum();
        System.out.println("sum: " + sum);

        System.out.println("================================================");
    }

    /**
     * Apply operation to nested collections e.g. list of lists or list of maps
     */
    private static void flatMap() {
        System.out.println("\n+flatMap+\n");
        // first example using a list of 2 x lists
        List<List<String>> list = Arrays.asList(
                friends,
                friends.stream().map(String::toUpperCase).collect(Collectors.toList()));
        System.out.println(list);

        list.stream()
                .flatMap(Collection::stream)
                .forEach(System.out::println);

        // second example using a list of maps, typical of what you get from as database results
        List<Map<String, Object>> listOfMaps = new ArrayList<>();
        HashMap<String, Object> hashMap1 = new HashMap<>();
        hashMap1.put("name", "Mike");
        hashMap1.put("age", 46);
        hashMap1.put("town", "Melksham");
        listOfMaps.add(hashMap1);

        HashMap<String, Object> hashMap2 = new HashMap<>();
        hashMap2.put("name", "Bob");
        hashMap2.put("age", 22);
        hashMap2.put("town", "Glasgow");
        listOfMaps.add(hashMap2);

        HashMap<String, Object> hashMap3 = new HashMap<>();     // no age key on this result
        hashMap3.put("name", "Danny");
        hashMap3.put("town", "Seend");
        listOfMaps.add(hashMap3);

        listOfMaps.stream()
                .filter(m->m.containsKey("age"))        // filter on map entries that have 'age' key
                .flatMap(m-> m.entrySet().stream())     // flatten list of maps
                .forEach(System.out::println);

        System.out.println("================================================");
    }

    /**
     * mapToInt returns an IntStream consisting of the results of applying the given function to the elements of this stream
     * mapToLong returns a LongStream consisting of the results of applying the given function to the elements of this stream
     * mapToDouble returns a DoubleStream consisting of the results of applying the given function to the elements of this stream
     */
    private static void mapTo() {
        System.out.println("\n+mapTo+\n");
        friends.stream()
            .mapToInt(String::length)
            .forEach(System.out::println);

        friends.stream()
                .mapToLong(s->Math.abs( (long) s.length()))
                .forEach(System.out::println);

        friends.stream()
                .mapToDouble(s->Double.parseDouble(String.valueOf(s.lastIndexOf("a"))))
                .forEach(System.out::println);

        System.out.println("================================================");
    }

    /**
     * Map returns a stream consisting of the results of applying the given function to the elements of this stream.
     */
    private static void map() {
        System.out.println("\n+map+\n");
        // here map output type matches input
        friends.stream()
            .map(String::toUpperCase)
            .forEach(System.out::println);

        // here map output type is boolean
        friends.stream()
                .map(s -> s.startsWith("N"))
                .forEach(System.out::println);

        System.out.println("================================================");
    }

    /**
     * filter returns a stream consisting of the elements of this stream that match the given predicate
     */
    private static void filter() {
        System.out.println("\n+filter+\n");
        List<String> onlyNs = friends.stream()
                .filter(s -> s.startsWith("N"))
                .collect(Collectors.toList());

        assert onlyNs.containsAll( Arrays.asList("Nate", "Neal") );

        System.out.println("================================================");
    }

    /**
     * Various ways to create a stream
     */
    private static void creatingStreams() {
        // empty
        Stream<String> streamEmpty = Stream.empty();

        // from collection
        Collection<String> collection = Arrays.asList("a", "b", "c");
        Stream<String> streamOfCollection = collection.stream();

        Stream<Integer> streamOfSingleElement = Stream.of(1);

        // stream of
        Stream<String> streamOf = Stream.of("a", "b", "c");

        // from full or partial array
        String[] arr = new String[]{"a", "b", "c"};
        Stream<String> streamOfArrayFull = Arrays.stream(arr);
        Stream<String> streamOfArrayPart = Arrays.stream(arr, 1, 3);

        // builder
        Stream<String> streamBuilder =
                Stream.<String>builder().add("a").add("b").add("c").build();

        // generate, following creates a sequence of 10 strings with value 'element'
        Stream<String> streamGenerated =
                Stream.generate(() -> "element").limit(10);

        // iterate, creates 40, 42, 44...
        Stream<Integer> streamIterated = Stream.iterate(40, n -> n + 2).limit(20);

        // IntStream, DoubleStream and LongStream allow creation of primitive type streams
        IntStream intStream = IntStream.range(1, 3); // range doesn't include the last element
        LongStream longStream = LongStream.rangeClosed(1, 3); // rangeClosed does include the last element

        // Random class has a bunch of methods for creating streams of primitives
        Random random = new Random();
        DoubleStream doubleStream = random.doubles(3);

        // from string, chars produces an IntStream not char
        "abc".chars().forEach(System.out::println);

        // concat
        System.out.println("\n+Stream.concat()+\n");
        Stream<Number> numbers = Stream.concat(Stream.of(1,2,3), Stream.of(4L,5L,6L));
        System.out.println( numbers.toList() );
    }
}
