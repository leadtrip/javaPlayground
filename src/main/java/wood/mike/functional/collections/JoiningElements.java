package wood.mike.functional.collections;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.joining;

public class JoiningElements {
    public static void main(String[] args) {
        final List<String> friends = Arrays.asList("Brian", "Nate", "Neal", "Raju", "Sara", "Scott");

        // =================================================================================
        // Old style, we end up with comma on end which has to be removed
        for(String name : friends) {
            System.out.print(name + ", ");
        }
        System.out.println();

        // Old style 2 - still naff
        for(int i = 0; i < friends.size() - 1; i++) {       // print all bar the last entry
            System.out.print(friends.get(i) + ", ");
        }
        if(friends.size() > 0)
            System.out.println(friends.get(friends.size() - 1)); // print the last entry

        // using functional interface
        System.out.println(String.join(", ", friends));

        // transform and join
        System.out.println(
                friends.stream()
                        .map(String::toUpperCase)
                        .collect(joining(", ", "Names are: ", ".")));
    }
}
