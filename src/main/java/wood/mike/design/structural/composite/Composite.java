package wood.mike.design.structural.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * A composite is an object designed as a composition of one-or-more similar objects, all exhibiting similar functionality.
 * The pattern creates an object containing a collection of the same type of object as itself and when a method common
 * to those objects is invoked on the composite object it invokes the same method on each object in the collection.
 */

// Client
public class Composite {
    public static void main(String[] args) {
        Exercise dip = new Dip();
        Exercise pressUp = new PressUp();
        Workout workout = new Workout( dip, pressUp );
        workout.perform();
    }
}

// Component
interface Exercise {
    default void perform() {
        System.out.println(getClass().getSimpleName());
    }
}

// Leaf
class Dip implements Exercise{}

// Leaf
class PressUp implements Exercise {}

// Composite
class Workout implements Exercise {
    List<Exercise> exercises = new ArrayList<>();

    Workout( Exercise... exercises ) {
        this.exercises.addAll(List.of(exercises));
    }

    @Override
    public void perform() {
        exercises.forEach(Exercise::perform);
    }
}




