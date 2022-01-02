package wood.mike.functional;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * Lambda expressions can be stored in variables for reuse
 */
public class ReusingLambdaExpressions {

    public static void main(String[] args) {
        final List<String> friends = Arrays.asList("Brian", "Nate", "Neal", "Raju", "Sara", "Scott");
        final List<String> editors = Arrays.asList("Brian", "Jackie", "John", "Mike");
        final List<String> comrades = Arrays.asList("Kate", "Ken", "Nick", "Paula", "Zach");

        // Can store lambda expression in variable in following ways, just getting more concise (less readable?) as we go down
        // Just for show here, no benefit of one over other
        final Predicate<String> isN = (String s) -> s.startsWith("N");
        final Predicate<String> isN2 = (s) -> s.startsWith("N");
        final Predicate<String> isN3 = s -> s.startsWith("N");

        // Lambda expression reused below
        final long countFriendsStartN =
                friends.stream()
                        .filter(isN3)
                        .count();

        final long countEditorsStartN =
                editors.stream()
                        .filter(isN3)
                        .count();

        final long countComradesStartN =
                comrades.stream()
                        .filter(isN3)
                        .count();
    }
}
