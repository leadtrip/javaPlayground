package wood.mike.functional.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Stream map method returns a collection the same size as the input collection
 * The output collection does not need to match the input
 */
public class TransformList {

    public static void main(final String[] args) {
        final List<String> friends = Arrays.asList("Brian", "Nate", "Neal", "Raju", "Sara", "Scott");

        // Imperative 1
        {
            final List<String> uppercaseNames = new ArrayList<String>();
            for(String name : friends) {
                uppercaseNames.add(name.toUpperCase());
            }
            System.out.println(uppercaseNames);
        }

        // Imperative 2
        {
            final List<String> uppercaseNames = new ArrayList<String>();
            friends.forEach(name -> uppercaseNames.add(name.toUpperCase()));
            System.out.println(uppercaseNames);
        }

        // Functional 1 - map input and output is string
        friends.stream()
                .map(name -> name.toUpperCase())
                .forEach(name -> System.out.print(name + " "));

        // Functional 2 - map input is string and output is integer
        friends.stream()
                .map(name -> name.length())
                .forEach(count -> System.out.print(count + " "));

        // Functional 3 - using method references rather than lambdas
        friends.stream()
                .map(String::toUpperCase)
                .forEach(System.out::println);

    }
}
