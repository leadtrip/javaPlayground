package wood.mike.misc;

import java.util.List;

/**
 * Compile time polymorphism aka static polymorphism is achieved in Java through method overloading, same method name with different arguments
 * Methods rpe and rpeDescription are overloaded in Exercise
 *
 * Runtime polymorphism aka dynamic binding/dynamic method dispatch is achieved in Java with method overriding, where a subclass implements a parent class method
 * perform and difficulty are overridden in subclasses of Exercise
 *
 */
public class Polymorphism {
    public static void main(String[] args) {
        List.of(new Deadlift(), new Squat(), new Crunch(), new BenchPress(), new DubellCurl()).forEach(
                exercise -> {
                    var reps = 12;
                    exercise.perform();
                    System.out.println( exercise.rpeDescription( reps ));
                    System.out.println( exercise.rpe( reps ) );
                    System.out.println("--------");
                }
        );

        var reps = 3;
        var kg = 100;
        var oneRm = 100;
        Exercise exercise = new Deadlift();
        exercise.describe(reps, kg, oneRm);

        kg = 15;
        oneRm = 30;
        exercise = new DubellCurl();
        exercise.describe(reps, kg, oneRm);
    }
}

interface Exercise {
    Integer MAX_RPE = 10;

    default String perform() {
        return "Exercising";
    }

    Integer difficulty();

    default String rpeDescription( final Integer reps, final Integer kg, final Integer currentOneRepMax ) {
        return description( rpe( reps, kg, currentOneRepMax ) );
    }

    default String rpeDescription( final Integer reps ) {
        return description( rpe( reps ) );
    }

    private String description( final Integer rpe ) {
        return switch ( rpe ) {
            case 2 -> "Easy";
            case 3 -> "Moderate";
            case 4 -> "Somewhat hard";
            case 5 -> "Hard";
            case Integer r when r < 2 -> "Very easy";
            case Integer r when r < 8 -> "Very hard";
            case Integer r when r < 11 -> "Maximum exertion";
            default -> "Mind blowing";
        };
    }

    /**
     * Nonsense calculation, just for fun
     * Generic, how hard is this exercise going to be for anyone for the given number of reps
     * @param reps - the number of reps to perform
     */
    default Integer rpe( final Integer reps ) {
        return (reps + (reps * difficulty()/MAX_RPE)) / 2;
    }

    /**
     * Again, nonsense calculation, just for fun
     * How hard will this exercise be for the exerciser with the given amount of weight given their current 1RM
     * @param reps              - the number of reps to perform
     * @param kg                - the amount of weight on bar/machine...
     * @param currentOneRepMax  - the exerciser's current one rep max for this exercise
     */
    default Integer rpe( final Integer reps, final Integer kg, final Integer currentOneRepMax ) {
        return (kg / currentOneRepMax * MAX_RPE) + reps;
    }

    default void describe( final Integer reps, final Integer kg, final Integer currentOneRepMax ) {
        System.out.printf( "%s %skg, for %s reps, current 1RM %skg, RPE is %s%n", perform(), kg, reps, currentOneRepMax, rpeDescription( reps, kg, currentOneRepMax ) );
    }
}

class Deadlift implements Exercise {

    @Override
    public String  perform() {
        return "Deadlifting";
    }

    @Override
    public Integer difficulty() {
        return 10;
    }
}

class Squat implements Exercise {
    @Override
    public Integer difficulty() {
        return 8;
    }
}

class Crunch implements Exercise {
    @Override
    public String perform() {
        return  "Crunching";
    }

    @Override
    public Integer difficulty() {
        return 5;
    }
}

class BenchPress implements Exercise {
    @Override
    public String perform() {
        return  "Bench pressing";
    }

    @Override
    public Integer difficulty() {
        return 7;
    }
}

class DubellCurl implements Exercise {

    @Override
    public String perform() {
        return "Dumbell curling";
    }

    @Override
    public Integer difficulty() {
        return 4;
    }
}