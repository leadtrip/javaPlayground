package wood.mike.misc;

import wood.mike.helper.Person;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class PriorityQueueExercise {

    public static void main(String[] args) {
        naturalOrdering();
        comparatorOrdering();
        comparingPersons();
    }

    /**
     * Natural ordering, no comparator specified
     */
    static void naturalOrdering() {
        PriorityQueue<Integer> integerQueue = new PriorityQueue<>();
        integerQueue.add(10);
        integerQueue.add(20);
        integerQueue.add(15);
        integerQueue.add(2);

        var startingSize = integerQueue.size();
        for( int i = 0 ; i < startingSize ; i++ ) {
            System.out.println(integerQueue.poll());
        }

        System.out.println("--------------------------------");
        // -----------------------------------

        PriorityQueue<String> stringQueue = new PriorityQueue<>();
        stringQueue.add("Z");
        stringQueue.add("b");
        stringQueue.add("z");
        stringQueue.add("r");
        stringQueue.add("E");
        stringQueue.add("A");

        startingSize = stringQueue.size();
        for( int i = 0 ; i < startingSize ; i++ ) {
            System.out.println(stringQueue.poll());
        }
    }

    /**
     * Compare persons based on age ascending
     * Add .reversed() to comparingLong to reverse the order
     */
    static void comparingPersons() {
        PriorityQueue<Person> pQueue = new PriorityQueue<>(Comparator.comparingLong(Person::getAge));
        pQueue.add(new Person.PersonBuilder("Jim", "Plank" ).dob( "1975-04-10" ).build());
        pQueue.add(new Person.PersonBuilder("Maggie", "Plank" ).dob( "1980-11-06" ).build());
        pQueue.add(new Person.PersonBuilder( "Arnold", "Plank" ).dob( "2005-01-30" ).build());
        pQueue.add(new Person.PersonBuilder( "Seb", "Plank" ).dob( "2001-12-14" ).build());
        pQueue.add(new Person.PersonBuilder( "Carlos", "Plank" ).dob( "2000-05-15" ).build());

        var startingSize = pQueue.size();
        for( int i = 0 ; i < startingSize ; i++ ) {
            System.out.println(pQueue.poll());
        }
    }

    /**
     * Order by the first element in the array
     */
    static void comparatorOrdering() {
        PriorityQueue<int[]> pQueue = new PriorityQueue<>(Comparator.comparingInt(e -> e[0]));

        pQueue.add(new int[] { 2, 2 });
        pQueue.add(new int[] { 2, 0 });
        pQueue.add(new int[] { 2, 1 });
        pQueue.add(new int[] { 1, 2 });
        pQueue.add(new int[] { 0, 1 });
        pQueue.add(new int[] { 0, 2 });
        pQueue.add(new int[] { 1, 0 });
        pQueue.add(new int[] { 0, 0 });
        pQueue.add(new int[] { 1, 1 });

        var startingSize = pQueue.size();
        for( int i = 0 ; i < startingSize ; i++ ) {
            System.out.println(Arrays.toString(pQueue.poll()));
        }
    }
}
