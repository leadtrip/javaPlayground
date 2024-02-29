package wood.mike.versions.java21;

import java.util.ArrayList;
import java.util.List;

public class Java21Features {

    public static void main(String[] args) {
        Java21Features java21Features = new Java21Features();
        java21Features.sequencedCollections();
        java21Features.recordPatterns();
        java21Features.switchPatternMatching(new Mouse("Slash", 62));
        java21Features.stringTemplates();
    }

    /**
     * All the common collection classes now implement SequencedCollection which offers new methods to get and add
     * elements at the first and last positions.
     */
    private void sequencedCollections() {
        List<Integer> al = new ArrayList<>();
        al.add(1);
        al.add(2);
        al.addLast(3);
        al.addFirst(0);
        al.getFirst();
        al.getLast();

        System.out.printf("List %s%n", al);
    }

    record Mouse(String name, int age) {}

    /**
     * An easier way of accessing a record's values
     */
    private void recordPatterns() {
        record Mouse(String name, int age) {}

        Mouse mark = new Mouse("Mark", 10);

        // old way of destructuring
        if (mark instanceof Mouse m) {
            String n = m.name();
            int a = m.age();
        }

        // new way
        if ( mark instanceof Mouse(String s, int a) ) {
            System.out.println("Mouse name " + s + " age " + a);
        }
    }

    private void switchPatternMatching(Object o) {
        switch (o) {
            case Mouse(String n, int a) -> System.out.printf("o is a Mouse: %s %d%n", n, a);
            case String s               -> System.out.printf("o is a String: %s%n", s);
            default                     -> System.out.printf("o is something else %s%n", o);
        }
    }

    /**
     * STR template processor is available in every class
     */
    private void stringTemplates() {
        String name = "Wesley";

        String message = STR."Hello \{name}";

        System.out.println(message);
    }
}
