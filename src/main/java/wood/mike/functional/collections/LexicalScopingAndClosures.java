package wood.mike.functional.collections;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;


public class LexicalScopingAndClosures {

    public static void main(String[] args) {
        final List<String> friends = Arrays.asList("Brian", "Nate", "Neal", "Raju", "Sara", "Scott");

        // what if we need to find people in the list whose names start with 2 or more different letters?
        // define a predicate for each like the following, not great as we've duplicated code
        final Predicate<String> startsWithN = name -> name.startsWith("N");
        final Predicate<String> startsWithB = name -> name.startsWith("B");

        final long countFriendsStartN =
                friends.stream()
                        .filter(startsWithN).count();

        final long countFriendsStartB =
                friends.stream()
                        .filter(startsWithB).count();

        // =====================================================================================================

        // as a first attempt to remove the duplication above, we've defined a static checkIfStartsWith higher order
        // function, adding static methods like this is not good though
        final long countFriendsStartN2 =
                friends.stream()
                        .filter(checkIfStartsWith("N")).count();
        final long countFriendsStartB2 =
                friends.stream()
                        .filter(checkIfStartsWith("B")).count();

        // =====================================================================================================

        /*
        Now we've created a Function that takes a String and returns a Predicate.
        But what’s the variable letter bound to? Since that’s not in the scope of this anonymous
        function, Java reaches over to the scope of the definition of this lambda
        expression and finds the variable letter in that scope. This is called lexical
        scoping. Lexical scoping is a powerful technique that lets us cache values
        provided in one context for use later in another context. Since this lambda
        expression closes over the scope of its definition, it’s also referred to as a closure
         */
        final Function<String, Predicate<String>> startsWithLetter =
                letter -> name -> name.startsWith(letter);

        // we call the single apply method on the Function in filter which returns the Predicate
        final long countFriendsStartN3 =
                friends.stream()
                        .filter(startsWithLetter.apply("N")).count();
        final long countFriendsStartB3 =
                friends.stream()
                        .filter(startsWithLetter.apply("B")).count();
    }

    public static Predicate<String> checkIfStartsWith(final String letter) {
        return name -> name.startsWith(letter);
    }
}
