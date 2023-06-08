package wood.mike.versions.java8;

import java.lang.reflect.Method;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.ValueRange;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Date and time related functionality added in Java 8
 */
public class Java8DateAndTime {

    public static void main(String[] args) {
        run();
    }

    static void run(){
        Java8DateAndTime java8DateAndTime = new Java8DateAndTime();
        for (Method m : java8DateAndTime.getClass().getMethods() ) {
            try {
                if ( m.getDeclaringClass() == java8DateAndTime.getClass() && m.getParameterCount() == 0) {
                    System.out.printf( "------------- %s -------------%n", m.getName() );
                    m.invoke(java8DateAndTime, null);
                }
            }
            catch( Exception e ) {
                e.printStackTrace();
            }
        }
    }

    public void yearMonth() {
        YearMonth dec2001 = YearMonth.of( 2001, 12 );
        System.out.println( dec2001.format( DateTimeFormatter.ofPattern( "MMM-yyyy" ) ) );

        System.out.printf( "December 2001 was %s days long%n", dec2001.lengthOfMonth() );

        var dec2001PlusOneMonth = dec2001.plusMonths( 1 );

        System.out.printf( "One month after December 2001 is %s %s%n", dec2001PlusOneMonth.getMonth(), dec2001PlusOneMonth.getYear() );
    }

    public void year() {
        Year thisYear = Year.now();
        Year nextYear = Year.now().plusYears( 1 );
        System.out.println( thisYear.length() );
        System.out.println( thisYear.isLeap() );
        System.out.println( thisYear.isAfter( nextYear ) );

        IntStream.range(2000, 3000).boxed()
                .map(Year::of)
                .filter(Year::isLeap)
                .filter(year -> year.getValue() % 100 == 0)
                .forEach(y -> System.out.printf("%s is a leap year%n", y));

        Year year2000 = Year.of( 2000 );
        System.out.println( year2000.format( DateTimeFormatter.ofPattern( "y" ) ) );

        YearMonth may2000 = year2000.atMonth(Month.MAY);
        System.out.println( may2000.format( DateTimeFormatter.ofPattern( "MMM-yyyy" ) ) );

        LocalDate may152000 = year2000.atMonthDay( MonthDay.of(Month.MAY, 15) );
        System.out.println( may152000.format( DateTimeFormatter.ofPattern( "dd-MM-yyyy" ) ) );
    }

    public void zonedDateTime() {
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

    // A time-zone offset is that the amount of your time that a time-zone differs from Greenwich/UTC
    public void zoneOffset() {
        ZoneOffset zo1 = ZoneOffset.UTC;
        System.out.println(zo1);

        ZoneOffset plus6Hours25Mins = ZoneOffset.ofHoursMinutes(6, 25 );
        System.out.println( plus6Hours25Mins );

        ZoneOffset plus10Hours = ZoneOffset.of( "+10" );
        System.out.println( plus10Hours );
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

    // measures time in years, months and days
    public void period() {
        Period p100d = Period.ofDays( 100 );
        Temporal ldt100Days = p100d.addTo(LocalDate.now());
        System.out.println( DateTimeFormatter.ofPattern("dd-MM-yyyy").format(ldt100Days) );

        Period threeThousandYears5Months13Days = Period.of(3000, 5, 13 );
        Temporal ldt3000Years = threeThousandYears5Months13Days.addTo(LocalDate.now());
        System.out.println( threeThousandYears5Months13Days );

        System.out.println( LocalDate.now().plus(threeThousandYears5Months13Days) );
        System.out.printf( "%s millenia between now and %s%n", ChronoUnit.MILLENNIA.between(LocalDate.now(), ldt3000Years), ldt3000Years );

        List<LocalDate> dates = new ArrayList<>();
        dates.add( next( Month.JANUARY.getValue(), 30 ) );
        dates.add( next( Month.APRIL.getValue(), 10 ) );
        dates.add( next( Month.MAY.getValue(), 15 ) );
        dates.add( next( Month.NOVEMBER.getValue(), 6 ) );
        dates.add( next( Month.DECEMBER.getValue(), 14 ) );

        Map<LocalDate, Long> daysTill = dates.stream()
                .collect(Collectors.toMap(ld -> ld, ld -> ChronoUnit.DAYS.between(LocalDate.now(), ld)));

        daysTill.forEach((key, value) -> System.out.printf("%s days until %s%n", value, key));
    }

    private LocalDate next( int month, int day ) {
        var d = LocalDate.of( Year.now().getValue(), month, day );
        return d.plusYears( ChronoUnit.DAYS.between( LocalDate.now(), d ) <= 0 ? 1 : 0 );
    }

    public void duration() {
        Duration d1 = Duration.between(LocalTime.MAX,LocalTime.NOON);
        System.out.println(d1);

        Duration duration = Duration.ofDays(49).plusHours(4).plusMinutes(38);
        System.out.println( duration.dividedBy(Duration.ofMinutes( 60 * 24 )) );

        Duration between = Duration.between(LocalTime.of( 18, 0 ), LocalTime.of( 20, 53 ));
        System.out.println( between.get( ChronoUnit.SECONDS ) );
    }

    public void instant() {
        var localFeb = ZonedDateTime.of( 2022, Month.FEBRUARY.getValue(), 24, 12, 0, 0, 0,
                ZoneId.systemDefault() );
        var usaFeb = ZonedDateTime.of( 2022, Month.FEBRUARY.getValue(), 24, 6, 0, 0, 0 ,
                ZoneId.of( "America/Chicago" ) );

        // instants should be the same as we've applied the normal 6-hour difference in the date
        System.out.printf( "Duration between local and USA instant %s%n", Duration.between( localFeb.toInstant(), usaFeb.toInstant() ) );

        // daylight savings start on 16th March in US & not till 27th March in UK so there'll be an 60-minute difference
        var localMar17 = ZonedDateTime.of( 2022, Month.MARCH.getValue(), 17, 12, 0, 0, 0,
                ZoneId.systemDefault() );
        var usaMar17 = ZonedDateTime.of( 2022, Month.MARCH.getValue(), 17, 6, 0, 0, 0 ,
                ZoneId.of( "America/Chicago" ) );

        System.out.printf( "Duration between local and USA instant %s%n", Duration.between( localMar17.toInstant(), usaMar17.toInstant() ) );

        Instant twoHoursFromNow = Instant.now().plus(2, ChronoUnit.HOURS);
        System.out.println( twoHoursFromNow.atZone( ZoneId.of("America/New_York") ) );
        System.out.println( twoHoursFromNow.atZone( ZoneId.of("Asia/Kamchatka") ) );
        System.out.println( twoHoursFromNow.atZone( ZoneId.of("Africa/Nairobi") ) );
    }

    public void dayOfWeek() {
        DayOfWeek dayOfWeek = DayOfWeek.of(3);
        System.out.println( dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault()));

        Arrays.stream(DayOfWeek.values()).sorted()
                .forEach(dayOfWeek1 -> System.out.println(dayOfWeek1.getDisplayName(TextStyle.FULL_STANDALONE, Locale.FRENCH)));

        DayOfWeek thirtyJan2005 = DayOfWeek.from(LocalDate.of(2005, 1, 30));
        System.out.printf("Thirty Jan 2005 was a %s%n", thirtyJan2005.getDisplayName(TextStyle.SHORT_STANDALONE, Locale.ENGLISH));
    }

    public void formatting() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy h:m:s a");

        System.out.println( formatter.format(LocalDateTime.now()) );

        System.out.println( LocalDateTime.parse( "04/08/2022 7:59:0 am", formatter) );
    }
}
