package wood.mike.functional;

import wood.mike.helper.Person;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public class ImplementingComparator {

    public static void main(String[] args) {
        final List<Person> people = Arrays.asList(
                new Person("John", "Biggs", LocalDate.of(2002, Month.JANUARY, 2)),
                new Person("Sara", "Cheeseboard", LocalDate.of(2004, Month.APRIL, 4)),
                new Person("Jane", "Falcon", LocalDate.of(2006, Month.MARCH, 23)),
                new Person("Greg", "Supper", LocalDate.of(1987, Month.JANUARY, 30)));

        List<Person> ascendingAge =
                people.stream()
                        .sorted(Person::ageDifference)
                        .collect(toList());
        printPeople("Sorted in ascending order by age: ", ascendingAge);

        printPeople("Sorted in descending order by age: ",
                people.stream()
                        .sorted((person1, person2) -> person2.ageDifference(person1))
                        .collect(toList()));

        // a comparator can be reversed like following rather than defining as above
        Comparator<Person> compareAscending = Person::ageDifference;
        Comparator<Person> compareDescending = compareAscending.reversed();

        printPeople("Sorted in ascending order by age: ",
                people.stream()
                        .sorted(compareAscending)
                        .collect(toList())
        );
        printPeople("Sorted in descending order by age: ",
                people.stream()
                        .sorted(compareDescending)
                        .collect(toList())
        );

        // here we're making use of the static convenience comparing method in Comparator
        final Function<Person, String> byName = person -> person.getName();
        printPeople("By name:",
        people.stream()
                .sorted(comparing((byName))).collect(toList()));

        // now sorting by multiple comparisons
        final Function<Person, Long> byAge = Person::getAge;
        final Function<Person, String> byTheirName = Person::getName;
        printPeople("Sorted in ascending order by age and name: ",
                people.stream()
                        .sorted(comparing(byAge).thenComparing(byTheirName))
                        .collect(toList()));
    }

    public static void printPeople(
            final String message, final List<Person> people) {
        System.out.println(message);
        people.forEach(System.out::println);
        System.out.println("-------------------");
    }
}
