package wood.mike.java8;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Java8Features {

    public static void main(String[] args) {
        interfaceChanges();
        dateAndTimeApi();
    }

    static void interfaceChanges() {
        var bmw = new BMW();
        System.out.printf( "%s is %s%n", bmw.brand(), BmwGroup.brandingTag() );

        var rr = new RollsRoyce();
        System.out.printf("%s is owned by %s%n", rr.brand(), rr.groupName());

        // here we make use of the functional interface and create a lambda expression for Mini
        BmwGroup mini = () -> { return "Mini"; };
        System.out.printf("%s is yet another %s brand%n", mini.brand(), mini.groupName());

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
     * This one only has a single abstract method so it can be a functional interface too
     */
    @FunctionalInterface
    interface BmwGroup{
        default String groupName() {
            return "BMW group";
        }

        static String brandingTag() {
            return "a BMW group company";
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

    static void dateAndTimeApi() {
        localDate();
        localTime();
    }

    static void localTime() {
        var now = LocalTime.now();
        System.out.printf("It's %s:%s:%s%n", now.getHour(), now.getMinute(), now.getSecond());
    }

    static void localDate() {
        var agesAway = LocalDate.of(4000, 4, 4);
        var birthday = LocalDate.parse("1975-04-10");
        var now = LocalDate.now();
        var nextYear = LocalDate.now().plusYears( 1 );
        var twentyYearsAgo = LocalDate.now().minus( 10, ChronoUnit.YEARS );
        var older = twentyYearsAgo.isBefore( now );

        DayOfWeek dow = birthday.getDayOfWeek();
        System.out.printf("I was born on a %s%n", dow.getDisplayName(TextStyle.FULL, Locale.getDefault()));
    }
}
