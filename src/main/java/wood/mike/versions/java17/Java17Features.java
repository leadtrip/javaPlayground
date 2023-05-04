package wood.mike.versions.java17;

import wood.mike.helper.Person;

import java.util.List;

public class Java17Features {
    public static void main(String[] args) {
        Java17Features java17Features = new Java17Features();
        java17Features.switchPatternMatching();
    }

    private void switchPatternMatching() {
        patternMatch(983129 );
        patternMatch(8989180120303L );
        patternMatch( "stringy" );
        patternMatch( new Person.PersonBuilder( "Ed", "The duck", "1901-03-22" ).build() );
        patternMatch(null );

        patternMatchDominance(new ArrayIndexOutOfBoundsException());
        List.of(new Fenix6(), new Pace2()).forEach(Java17Features::patternMatchingCompleteness);

        // REMOVED IN JAVA 19
        //guardedPatterns(new Person.PersonBuilder("Brian", "Cox" ).dob( "1973-02-13" ).build());
    }

    /**
     * With pattern matching, selection is determined by pattern matching rather than by an equality check
     * Note the default case is required for completeness to cover all possibilities
      */
    private static void patternMatch( Object o ) {
        switch (o) {
            case Integer i -> System.out.printf("%s%n", Integer.toHexString(i) );
            case Long l -> System.out.printf( "%s%n", Long.toBinaryString(l) );
            case Person p -> System.out.printf("%s%n", p.getName() );
            case null -> System.out.println("NULL");
            default -> System.out.printf( "It's a %s%n", o.getClass() );
        }
    }

    /**
     * It is possible for the selector expression to match multiple labels in a switch block.
     * Below Exception case dominates any sub classes of Exception so ordering is required and causes compile time error if violated
      */
    private static void patternMatchDominance( Throwable t ) {
        switch (t) {
            case UnsupportedOperationException uoe -> System.out.println( "UnsupportedOperationException" );
            case Exception ex -> System.out.println("Exception");
            // Can't do the following because case with Exception above dominates RuntimeException, basically we'd never get here RuntimeException is an Exception
            // case RuntimeException re -> System.out.println("RuntimeException");
            case Throwable th -> System.out.println("Throwable");
            // also can't do below because Throwable case above is a catch all
            //default -> System.out.println( "It's a " + t.getClass() );
        }
    }

    /**
     * REMOVED IN JAVA 19
     *
     * Conditional logic is added to the case label aka guarded pattern of form p && e (pattern && expression)
     */
/*    private static void guardedPatterns( Person person ) {
        switch (person) {
            case Person p && p.getAge() < 18 -> System.out.println("Child");
            case Person p && p.getAge() >= 65 -> System.out.println("Pensioner");
            case Person ignored -> System.out.println( "Standard" );
        }
    }*/

    /**
     * If the type of the selector expression is a sealed class,
     * then the type coverage check can take into account the permits clause of the
     * sealed class to determine whether a switch block is complete
     */
    private static void patternMatchingCompleteness( BatteryLife b ) {
        switch (b) {
            case Fenix6 f -> f.playMusic();
            case Pace2 p -> p.trainWithPower();
            // no need for default, every case has been captured although this could be added and a case above removed which isn't a compilation issue
        }
    }

    sealed abstract class Watch implements BatteryLife {
        void stopWatch() {
            System.out.println("Stopwatch running");
        }

        void trainWithHeartRate(){
            System.out.println("Run 20 minutes at 130-150 BPM");
        }
    }

    sealed interface BatteryLife permits Watch {
        Integer averageBatteryLifeHours();
    }

    sealed abstract class Garmin extends Watch {
        abstract void playMusic();
    }

    sealed abstract class Coros extends Watch{
        abstract void trainWithPower();
    }

    final class Fenix6 extends Garmin {
        public Integer averageBatteryLifeHours() {
            return 10 * 24 * 60;
        }

        @Override
        public void playMusic() {
            System.out.println("Playing Welcome to the jungle");
        }
    }

    final class Pace2 extends Coros {
        public Integer averageBatteryLifeHours() {
            return 12 * 24 * 60;
        }

        @Override
        void trainWithPower() {
            System.out.println("Run 30 minutes at 300 watts");
        }
    }
}
