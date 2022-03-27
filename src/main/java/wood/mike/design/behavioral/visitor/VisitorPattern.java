package wood.mike.design.behavioral.visitor;

import java.util.List;

/**
 * Perform one or more operations on a collection of different data types without disrupting existing code.
 */
public class VisitorPattern {
    public static void main(String[] args) {
        Athlete athlete = new Athlete( 47, 184, 75 );

        Run run = new Run( 5, 30, 200 );
        Swim swim = new Swim( 60, 4000, "freestyle" );
        Gym gym = new Gym( 25, 70, 4, 60 );

        List<Visitable> exercises = List.of( run, swim, gym );

        CaloriesVisitor caloriesVisitor = new CaloriesVisitor( athlete );
        exercises.forEach( visitable -> visitable.accept(caloriesVisitor));
    }
}

// -------------------------------------------------------------
// base visitor, when we add another sport we add another visit method for that sport and implement in concrete classes

interface Visitor {
    void visit(Run run);
    void visit(Swim swim);
    void visit(Gym gym);
}

// -------------------------------------------------------------
// concrete visitor

class CaloriesVisitor implements Visitor {

    Athlete athlete;

    public CaloriesVisitor(Athlete athlete) {
        this.athlete = athlete;
    }

    @Override
    public void visit(Run run) {
        System.out.println( "Calculate calories using run metrics" );
    }

    @Override
    public void visit(Swim swim) {
        System.out.println( "Calculate calories using swim metrics" );
    }

    @Override
    public void visit(Gym gym) {
        System.out.println( "Calculate calories using gym metrics" );
    }
}

// -------------------------------------------------------------

interface Visitable{
    void accept(Visitor visitor);
}

// -------------------------------------------------------------
// concrete visitable implementations

class Run implements Visitable{
    Integer speed;
    Integer duration;
    Integer elevation;

    public Run(Integer speed, Integer duration, Integer elevation) {
        this.speed = speed;
        this.duration = duration;
        this.elevation = elevation;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

// -------------------------------------------------------------

class Swim implements Visitable{
    Integer duration;
    Integer distanceMetres;
    String stroke;

    public Swim(Integer duration, Integer distanceMetres, String stroke) {
        this.duration = duration;
        this.distanceMetres = distanceMetres;
        this.stroke = stroke;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

// -------------------------------------------------------------

class Gym implements Visitable{
    Integer sets;
    Integer reps;
    Integer rpe;
    Integer duration;

    public Gym(Integer sets, Integer reps, Integer rpe, Integer duration) {
        this.sets = sets;
        this.reps = reps;
        this.rpe = rpe;
        this.duration = duration;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

// -------------------------------------------------------------
// not related to visitor pattern

class Athlete {
    Integer age;
    Integer heightCms;
    Integer weightKgs;

    public Athlete(Integer age, Integer heightCms, Integer weightKgs) {
        this.age = age;
        this.heightCms = heightCms;
        this.weightKgs = weightKgs;
    }
}