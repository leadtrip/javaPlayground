package wood.mike.functional;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * Imperative programming is a programming paradigm that uses statements that change a program’s state.
 * Code that specifies the steps that the computer must take to accomplish the goal
 *
 * Functional programming involves composing the problem as a set of functions to be executed.
 * You define carefully the input to each function, and what each function returns.
 *
 * A functional interface must have one abstract—unimplemented—method.
 * It may have zero or more default or implemented methods. It may also have static methods.
 *
 * The Java compiler will take either a lambda expression or a reference to a method where an implementation
 * of a functional interface is expected
 */

public class Iterating {

    public static void main(String[] args) {
        final List<String> friends = Arrays.asList("Brian", "Nate", "Neal", "Raju", "Sara", "Scott");

        // Imperative 1 - for loop
        for(int i = 0; i < friends.size(); i++) {
            System.out.println(friends.get(i));
        }

        // Imperative 2 - enhanced for loop
        for(String name : friends) {
            System.out.println(name);
        }

        // Functional 1 - passing an anonymous class to the Iterator's forEach method
        friends.forEach(new Consumer<String>() {
            public void accept(final String name) {
                System.out.println(name);
            }
        });

        // Functional 2 - no need to define the anonymous class as per above as the Consumer is a functional interface
        // with the single accept method so we can use a lambda expression
        friends.forEach(name -> System.out.println(name));

        // Functional 3 - going a step further, we can use a method reference
        friends.forEach(System.out::println);
    }
}
