package wood.mike.functional.collections;

import com.google.common.base.Strings;
import wood.mike.helper.Person;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Stream filter method expects a lambda expression that returns a boolean result.
 * Unlike map, the returned collection may be size zero up to the same size as the input collection
 */
public class FindingElements {

    public static void main(String[] args) {
        final List<String> friends = Arrays.asList("Brian", "Nate", "Neal", "Raju", "Sara", "Scott");

        // Imperative
        final List<String> startsWithNImperative = new ArrayList<>();
        for(String name : friends) {
            if(name.startsWith("N")) {
                startsWithNImperative.add(name);
            }
        }

        // Functional
        final List<String> startsWithNFunctional =
                friends.stream()
                        .filter( s -> s.startsWith("N") )
                        .collect(Collectors.toList());

        System.out.println(String.format("Found %d names", startsWithNFunctional.size()));

        mapExamples();
    }

    /**
     * A bunch of examples that kinda mirror handling database results.
     * Starting with handling elements of the map directly then converting to objects & getting more flexible with method signatures
     */
    private static void mapExamples() {

        var deadList =
                dbResults().stream()
                .filter(m -> m.containsKey("dod"))
                .map(m -> m.get("forename").toString() + " " + m.get("surname"))
                .collect(Collectors.toList());

        System.out.printf("Dead people: %s", deadList);
        System.out.println("\n----------------------");

        var age = 47;
        System.out.printf( "Older than %d: %s" ,age, olderThan( dbResults(), age ) );
        System.out.println("\n----------------------");

        System.out.printf("All people: %s ", getPeople( dbResults(), person -> true ));
        System.out.println("\n----------------------");

        System.out.printf("Alive people: %s ", getPeople( dbResults(), person -> person.getDod() == null ));
        System.out.println("\n----------------------");

        System.out.printf("People born in April: %s ", getPeople( dbResults(), person -> person.getDob().getMonth() == Month.APRIL ));
        System.out.println("\n----------------------");

        var letter = "D";
        // get list of Person
        System.out.printf("People starting with %s %s", letter, getPeeps(dbResults(), Person::new, person -> person.getName().startsWith( letter )) );
        System.out.println("\n----------------------");
        // get list of string
        System.out.printf("People starting with %s %s", letter, getPeeps(dbResults(), map -> new Person(map).getName(), name -> name.startsWith( letter )) );
        System.out.println("\n----------------------");
        // get list of Person
        System.out.printf("People younger than %s %s", age, getPeeps(dbResults(), Person::new, person -> person.getAge() < age ) );
        System.out.println("\n----------------------");
        var nameLen = 4;
        // get list of string
        System.out.printf("People with forename > %s chars %s", nameLen, getPeeps(dbResults(), map -> (String) map.get("forename"), name -> name.length() > nameLen ) );
        System.out.println("\n----------------------");
        // get list of Long
        System.out.printf("DB IDs: %s", getPeeps(dbResults(), map-> (Long)map.get("id"), anId -> true) );
        System.out.println("\n----------------------");

        getListFromCollection( dbResults(),Person::new, person -> person.getAge() < age );
        System.out.println("\n----------------------");

        // now we've removed the enforcement of passing a map as the stream source we can reverse what we've been doing
        // above and pass a list of Person and get a list of something else back, in this case dead people's DOD

        // first off create the list of Person
        List<Person> personList =
                dbResults()
                .stream()
                .map(Person::new)
                .collect(Collectors.toList());

        List<LocalDate> deadPeopleDod = getListFromCollection( personList, Person::getDod, Objects::nonNull );
        System.out.printf("Dead people's DOD: %s", deadPeopleDod );
        System.out.println("\n----------------------");

        // convert a Set of integers to hex strings
        List<String> hex = getListFromCollection(
                Set.of(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15),
                num -> "0x"+ Strings.padStart(Integer.toHexString(num),2, '0').toUpperCase(),
                hexString -> true );
        System.out.printf("Numbers to hex: %s", hex);
        System.out.println("\n----------------------");
    }

    /**
     * Working directly with list of maps, get people older than the given age
     * This is not very flexible as we've coded the predicate into the method, what if we wanted people younger than
     * a given age? The method would have to change.
     */
    private static List<String> olderThan( final List<Map<String, Object>> people, final Integer age ) {
        return people.stream()
                .filter( m -> ((LocalDate) m.get("dob")).plusYears(age).isBefore(LocalDate.now()) )
                .map( m -> m.get("forename").toString() + " " + m.get("surname") + " " + ChronoUnit.YEARS.between((LocalDate) m.get("dob"), LocalDate.now()))
                .collect(Collectors.toList());
    }

    /**
     * More flexible than above.
     * Convert list of maps to list of Person and apply predicate
     * Here the conversion from map to Person is mandated by the method and therefore the predicate has to handle Person
     * so there's greater flexibility than the method above but with some constraints on returned list type
     */
    private static List<Person> getPeople(final List<Map<String, Object>> people, final Predicate<Person> predicate) {
        return people.stream()
                .map(Person::new)
                .filter( predicate )
                .collect(Collectors.toList());
    }

    /**
     * Even more flexible than above.
     * Now we're not mandating any type to be returned or mapped to, only the input.
     * @param people    - list of map string->objects i.e. db rows
     * @param function  - how are we going to convert a map or db row
     * @param predicate - what are we filtering on
     * @param <T>       - the type to convert to and filter on
     * @return a list of Ts
     */
    private static <T> List<T> getPeeps(final List<Map<String, Object>> people,
                                        final Function<Map<String, Object>, T> function,
                                        final Predicate<T> predicate) {
        return people.stream()
                .map( function )
                .filter( predicate )
                .collect(Collectors.toList());
    }

    /**
     * Again more flexible than above.
     * Now we've removed the enforcement of passing a map as the stream source and any Collection can be passed
     */
    private static <T, E> List<T> getListFromCollection(
                                        final Collection<E> collection,
                                        final Function<? super E, ? extends T> function,
                                        final Predicate<T> predicate) {
        return collection.stream()
                .map( function )
                .filter( predicate )
                .collect(Collectors.toList());
    }

    /**
     * @return typical list of map to string (column name) -> object you'd get from a database layer
     */
    private static List<Map<String, Object>> dbResults() {
        Map<String, Object> dbRow1 = new HashMap<>();
        dbRow1.put("id", 1L);
        dbRow1.put("forename", "Mike");
        dbRow1.put("surname", "Wood");
        dbRow1.put("dob", LocalDate.of(1975, Month.APRIL, 10));

        Map<String, Object> dbRow2 = new HashMap<>();
        dbRow2.put("id", 2L);
        dbRow2.put("forename", "Stephen");
        dbRow2.put("surname", "Seagal");
        dbRow2.put("dob", LocalDate.of(1952, Month.APRIL, 10));

        Map<String, Object> dbRow3 = new HashMap<>();
        dbRow3.put("id", 3L);
        dbRow3.put("forename", "Daisy");
        dbRow3.put("surname", "Ridley");
        dbRow3.put("dob", LocalDate.of(1999, Month.APRIL, 10));

        Map<String, Object> dbRow4 = new HashMap<>();
        dbRow4.put("id", 4L);
        dbRow4.put("forename", "Elliot");
        dbRow4.put("surname", "Wood");
        dbRow4.put("dob", LocalDate.of(2005, Month.JANUARY, 30));

        Map<String, Object> dbRow5 = new HashMap<>();
        dbRow5.put("id", 5L);
        dbRow5.put("forename", "Christian");
        dbRow5.put("surname", "Bale");
        dbRow5.put("dob", LocalDate.of(1974, Month.JANUARY, 30));

        Map<String, Object> dbRow6 = new HashMap<>();
        dbRow6.put("id", 6L);
        dbRow6.put("forename", "Nancy");
        dbRow6.put("surname", "Sinatra");
        dbRow6.put("dob", LocalDate.of(1940, Month.JUNE, 8));

        Map<String, Object> dbRow7 = new HashMap<>();
        dbRow7.put("id", 7L);
        dbRow7.put("forename", "Joan");
        dbRow7.put("surname", "Rivers");
        dbRow7.put("dob", LocalDate.of(1933, Month.JUNE, 8));
        dbRow7.put("dod", LocalDate.of(2014, Month.SEPTEMBER, 4));

        Map<String, Object> dbRow8 = new HashMap<>();
        dbRow8.put("id", 8L);
        dbRow8.put("forename", "Desmond");
        dbRow8.put("surname", "Tutu");
        dbRow8.put("dob", LocalDate.of(1931, Month.OCTOBER, 7));
        dbRow8.put("dod", LocalDate.of(2021, Month.DECEMBER, 26));

        return List.of(dbRow1,dbRow2,dbRow3,dbRow4,dbRow5,dbRow6,dbRow7,dbRow8);
    }
}
