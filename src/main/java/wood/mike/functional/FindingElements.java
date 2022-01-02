package wood.mike.functional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Stream filter method expects a lambda expression that returns a boolean result.
 * Unlike map, the returned collection may be size zero up to the same size as the input collection
 */
public class FindingElements {

    public static void main(String[] args) {
        final List<String> friends = Arrays.asList("Brian", "Nate", "Neal", "Raju", "Sara", "Scott");

        // Imperative
        final List<String> startsWithNImperative = new ArrayList<>();
        for(String name : friends) {
            if(name.startsWith("N")) {
                startsWithNImperative.add(name);
            }
        }

        // Functional
        final List<String> startsWithNFunctional =
                friends.stream()
                        .filter( s -> s.startsWith("N") )
                        .collect(Collectors.toList());

        System.out.println(String.format("Found %d names", startsWithNFunctional.size()));
    }
}
