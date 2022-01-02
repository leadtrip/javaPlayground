package wood.mike.functional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.Stream;

public class ReduceCollectionToSingleValue {
    public static void main(String[] args) {
        final List<String> friends = Arrays.asList("Brian", "Nate", "Neal", "Raju", "Sara", "Scott");

        // =================================================================================
        // mapToInt accepts a ToIntFunction which takes an Object as input and returns an int
        ToIntFunction<String> doubleLength = (String s) -> s.length() * 2;

        System.out.println("Total number of characters in all names * 2: " +
            friends.stream()
                .mapToInt( doubleLength )           // map
                .sum());                            // reduce

        // =================================================================================

        final Optional<String> aLongName =
            friends.stream()
                .reduce((name1, name2) -> name1.length() >= name2.length() ? name1 : name2);

        aLongName.ifPresent(name -> System.out.printf("A longest name: %s%n", name));

        // =================================================================================

        // same as above but we're passing in a base or default value "Steve", no need for Optional as we'll always get a non-null result
        System.out.println( "Longest name is: " +
            Stream.of( "" )
                .reduce("Steve", (name1, name2) -> name1.length() >= name2.length() ? name1 : name2) );
    }
}
