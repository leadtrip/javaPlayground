package wood.mike.java8;

import java.lang.reflect.Method;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.ValueRange;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

/**
 * Features added in Java 8
 */
public class Java8Features {

    public static void main(String[] args) {
        run();
    }

    static void run(){
        Java8Features j8 = new Java8Features();
        for (Method m : j8.getClass().getMethods() ) {
            try {
                if ( m.getDeclaringClass() == j8.getClass() && m.getParameterCount() == 0) {
                    System.out.printf( "------------- %s -------------%n", m.getName() );
                    m.invoke(j8, null);
                }
            }
            catch( Exception e ) {
                e.printStackTrace();
            }
        }
    }

    public void interfaceChanges() {
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

    static void zonedDateTime() {
        var am = ZonedDateTime.now( ZoneId.of( "America/Chicago" ) );
        System.out.println( am.format( DateTimeFormatter.ofLocalizedDateTime( FormatStyle.LONG ) ) );
        System.out.println( am.format( DateTimeFormatter.ofLocalizedDateTime( FormatStyle.MEDIUM ) ) );
        System.out.println( am.format( DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT ) ) );
    }

    public void clock() {
        Clock c = Clock.systemDefaultZone();
        System.out.println(c.getZone());

        Clock am = Clock.system( ZoneId.of( "America/Chicago" ) );
        System.out.println( am );
    }

    public void offsetDateTime() {
        OffsetDateTime odtLocal = OffsetDateTime.now();
        ZoneOffset offsetLocal = odtLocal.getOffset();
        System.out.printf("Offset from GMT locally: %s%n", offsetLocal.getTotalSeconds());

        OffsetDateTime odtUsa = OffsetDateTime.now( ZoneId.of( "America/Chicago" ) );
        System.out.printf( "Time in St Louis: %s%n", odtUsa.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM)) );
        ZoneOffset offsetUsa = odtUsa.getOffset();
        System.out.printf( "Offset from GMT in St Louis: %s%n", offsetUsa );
    }

    public void offsetTime() {
        OffsetTime ot = OffsetTime.now();
        System.out.println( ot );

        var numberOfSecondsToTenAtNightTomorrow =
                ot.until(
                    ZonedDateTime.of(
                        LocalDateTime.of( LocalDate.now().plusDays(1),
                        LocalTime.of( 22, 0, 0, 0 ) ),
                            ZoneId.systemDefault() ),
                    ChronoUnit.SECONDS );
        System.out.println(numberOfSecondsToTenAtNightTomorrow);
        System.out.println( LocalDateTime.now().plusSeconds(numberOfSecondsToTenAtNightTomorrow).format(DateTimeFormatter.ISO_DATE_TIME) );
    }

    public void monthDay() {
        MonthDay md = MonthDay.now();
        System.out.printf("%s %s%n", md.getDayOfMonth(), md.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()));

        ValueRange r1 = md.range(ChronoField.DAY_OF_MONTH);
        System.out.println(r1);
    }

    public void localDateTime() {
        var tenThirty = LocalTime.of( 10, 30 );
        LocalDateTime localDateTime = tenThirty.atDate(LocalDate.ofYearDay(2005, 30 ));

        var thirtyFirstJan20050630 = LocalDateTime.of( 2005, Month.JANUARY, 31, 6, 30 );

        var localDate = LocalDate.of( 1930, 1, 20 );
        var localTime = LocalTime.of( 13, 48 );
        var localDateTimeFromLocalDateAndLocalTime =  LocalDateTime.of( localDate, localTime );

        var march2003 = LocalDateTime.parse( "2003-03-16T20:55" );
        printLdt( march2003, DateTimeFormatter.BASIC_ISO_DATE );
        printLdt( march2003, DateTimeFormatter.ISO_DATE_TIME );
        printLdt( march2003, DateTimeFormatter.ISO_DATE );
        printLdt( march2003, DateTimeFormatter.ISO_TIME );

        LocalDateTime.from( ZonedDateTime.now() );
    }

    public void printLdt( LocalDateTime ldt, DateTimeFormatter dtf ) {
        System.out.printf("%s%n", ldt.format(dtf));
    }

    public void localTime() {
        var now = LocalTime.now();
        System.out.printf("It's %s:%s:%s%n", now.getHour(), now.getMinute(), now.getSecond());

        var tenThirty = LocalTime.of( 10, 30 );
        var later = now.plusMinutes( 20 ).plusSeconds( 10 );
        var tenMinutesAgo = LocalTime.now().minusMinutes( 10 );
    }

    public void zoneId() {
        ZoneId zoneNy = ZoneId.of( "America/New_York" );
        ZoneId zoneLa = ZoneId.of( "America/Los_Angeles" );
        System.out.println( LocalTime.now( zoneNy ).format( DateTimeFormatter.ofLocalizedTime( FormatStyle.SHORT ) ) );
        System.out.println( LocalTime.now( zoneLa ).format( DateTimeFormatter.ofLocalizedTime( FormatStyle.SHORT ) ) );
    }

    public void localDate() {
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
