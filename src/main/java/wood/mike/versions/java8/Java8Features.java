package wood.mike.versions.java8;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Features added in Java 8
 */
public class Java8Features {

    public static void main(String[] args) {
        interfaceChanges();
        streamApi();
        forEach();
    }

    static void interfaceChanges() {
        var bmw = new BMW();
        System.out.println( bmw.brandingTag() );

        var rr = new RollsRoyce();
        System.out.printf("%s is owned by %s%n", rr.brand(), BmwGroup.groupName());

        // here we make use of the functional interface and create a lambda expression for Mini
        BmwGroup mini = () -> { return "Mini"; };
        System.out.printf("%s is yet another %s brand%n", mini.brand(), BmwGroup.groupName());
        System.out.println( mini.brandingTag() );

        // You use lambda expressions to create anonymous methods. Sometimes, however, a lambda expression does nothing but call an existing method.
        // In those cases, it's often clearer to refer to the existing method by name.
        // Method references enable you to do this; they are compact, easy-to-read lambda expressions for methods that already have a name.
        List<String> bmwGroupBrands =
                Stream.of(bmw, rr, mini)
                        .map(BmwGroup::brand) // call method reference
                        .peek(System.out::println).toList();
    }

    /**
     * Interfaces can have default and static methods
     * This one only has a single abstract method, so it is functional interface too
     */
    @FunctionalInterface
    interface BmwGroup{
        static String groupName() {
            return "BMW group";
        }

        default String brandingTag() {
            return String.format( "%s a %s company", brand(), groupName() );
        }

        String brand();
    }

    static class BMW implements BmwGroup {
        public String brand() {
            return "BMW";
        }
    }

    static class RollsRoyce implements BmwGroup{
        public String brand() {
            return "Rolls Royce";
        }
    }

    /**
     * Tons of examples elsewhere in this project, here's a few anyway
     */
    private static void streamApi() {

    }

    /**
     * forEach added to Iterable, accepts Consumer and applies action on each element
     */
    private static void forEach() {
        Consumer<Integer> longHexPrinter = i -> System.out.printf( "0x%08X%n", i );     // lambda style Consumer

        Consumer<Integer> shortHexPrinter = new Consumer<Integer>() {       // full definition style Consumer
            @Override
            public void accept(Integer i) {
                System.out.printf( "0x%04X%n", i );
            }
        };

        var numberList = List.of( 1, 2, 3, 4 );
        numberList.forEach(longHexPrinter);
        numberList.forEach(shortHexPrinter);
    }
}
