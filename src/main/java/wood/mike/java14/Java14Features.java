package wood.mike.java14;

public class Java14Features {
    public static void main(String[] args) {
        System.out.println( switchExpressions(3 ) );
        instanceofPatternMatching( "yo" );
    }

    /**
     * instanceof operator has been extended to take a type pattern instead of just a type
     */
    private static <I> void instanceofPatternMatching( final I input ) {
        // prior to pattern matching
        if (input instanceof String) {
            String str = (String) input;
            str.contains("o");
        }

        // pattern matching
        if( input instanceof String s ) {
            s.contains("o");
        }
    }

    /**
     * switch expressions have been around for a few releases but have been standardised in 14
     */
    private static String switchExpressions( final Integer num ) {
        // classic
        switch (num) {
            case 1:
                System.out.println("one");
                break;
            case 2:
                System.out.println("two");
                break;
            default:
                System.out.println("unsupported");
        }

        // enhanced
        switch (num) {
            case 1 -> System.out.println("un");
            case 2 -> System.out.println("deux");
            default -> System.out.println("non pris en charge");
        }

        // expression
        System.out.println(
                switch (num) {
                    case 1 -> "uno";
                    case 2 -> "dos";
                    default -> "sin apoyo";
                }
        );

        // use {} block and yield when right of -> requires something more complex than single expression
        return switch (num) {
            case 1 -> "eins";
            case 2 -> "zwei";
            default -> {
                var res = "nicht unterstützt";
                yield res;
            }

        };
    }
}
