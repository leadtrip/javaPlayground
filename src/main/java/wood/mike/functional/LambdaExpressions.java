package wood.mike.functional;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class LambdaExpressions {

    public static void main(String[] args) {
        // using Consumer from Java library
        Consumer<Integer> con1 = (n) -> {
            System.out.println(n);
        };

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        numbers.forEach(con1);

        // using Functional interface we've defined ourselves
        StringFunction exclaim = (s) -> s + "!";
        printFormatted("Hello", exclaim);

        // a more complex example using our interface & multiple lines, here we need to use {} to enclose the body
        StringFunction prefixSuffix = (s) -> {
            Integer i = 2313;
            String st = "--=-=0";
            return i + s.toUpperCase() + st;
        };
        printFormatted( "Hello", prefixSuffix );

        // single parameter lambda can be passed in parentheses
        printFormatted( "No", (str) -> str + " problem with parentheses" );
        // or without
        printFormatted( "No", str -> str + " problem without parentheses" );
        // can also specify type
        printFormatted( "No", (String str) -> str + " problem without parentheses" );
        // and from Java 11 use var
        printFormatted( "No", (var str) -> str + " problem without parentheses" );

        // for zero parameter method we just use ()
        whatever( () -> System.out.println( "pointless" ) );
    }

    @FunctionalInterface
    interface StringFunction {
        String run(String str);
    }

    public static void printFormatted(String str, StringFunction format) {
        String result = format.run(str);
        System.out.println(result);
    }

    @FunctionalInterface
    interface NoParameterFunction {
        void doSomething();
    }

    public static void whatever( NoParameterFunction npf ) {
        npf.doSomething();
    }
}


