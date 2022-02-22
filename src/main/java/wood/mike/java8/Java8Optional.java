package wood.mike.java8;

import wood.mike.helper.Person;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * A container object which may or may not contain a non-null value
 *
 * Note some of the features below were added in Java 9+
 */
public class Java8Optional {
    public static void main(String[] args) {
        optional();
    }

    private static void optional() {
        Optional<String> empty = Optional.empty();
        assert !empty.isPresent();

        String a1 = null;
        // Optional.of( a1 ); NullPointerException

        Optional<String> s1 = Optional.ofNullable( a1 ); // okay
        assert !s1.isPresent();

        a1 = "I'm here now";
        assert !s1.isPresent();     // just assigning a value to the wrapped object doesn't mean the Optional is updated
        s1 = Optional.ofNullable( a1 );
        assert s1.isPresent();

        assert !s1.isEmpty();      // can also do this as of java 11

        // conditional actions
        // ifPresent
        Optional<String> aha = Optional.of( "The sun always shines on TV" );
        aha.ifPresent(System.out::println);

        // orElse
        System.out.printf("%s the baptist%n", Optional.of( "John" ).orElse("Fred" ));
        String conan = null;
        System.out.printf("%s the barbarian%n", Optional.ofNullable( conan ).orElse( "Randy" ));

        // orElseGet
        assert "green" == Optional.ofNullable( null ).orElseGet( () -> "green" );
        System.out.printf("Method %s%n", Optional.ofNullable( null ).orElseGet( () -> List.of( "orElseGet", "returns", "result", "produced", "by", "supplier", "function" ) ) );

        // difference between orElse and orElseGet - orElse always executes even if element is not null
        String something = "something";
        System.out.println( Optional.ofNullable( something ).orElse( expensiveDbCall() ) ); // even though something is not null the call to orElse is made
        something = null;
        System.out.println( Optional.ofNullable( something ).orElse( expensiveDbCall() ) ); // we'd expect the call to orElse this time

        // orElseThrow, also no args version of this
        try{
            throwMeSomething();
        }
        catch ( UnsupportedOperationException u ) {
            System.out.println( "Thanks" );
        }

        // get - kinda ruins the point of Optional as reintroduces the possibility of getting null/exception
        try {
            Optional.ofNullable(null).get();
        }
        catch( NoSuchElementException n ) {
            System.out.println( "Pointless, use another retrieval method" );
        }

        // conditional - isPresent
        Person p = new Person( "Den", "Watts", LocalDate.of( 1950, 4, 10 ));
        System.out.printf("%s is %s a pensioner%n", p.getName(), isPensioner( p ) ? "" : "not");

        // map to transform
        System.out.printf("%s is %s%n", p.getName(), Optional.of( p )
                .map(person -> person.getDod() != null ? "dead" : "alive").get() );

        // flatmap
        Optional<Person> optionalPerson = Optional.of( p );         // person in Optional
        Optional<Optional<Person>> optOpt = Optional.of(optionalPerson);        // person nested in Optional again
        Optional<Optional<String>> nestedPersonName = optOpt.map(v -> v.map(Person::toString));  // map person name but leaves nesting
        Optional<String> personName = optOpt.flatMap(v -> v.map(Person::toString)); // flatMap person name and removes outer nesting

        Optional<Dude> dude1 = Optional.of( new Dude( "Bret" ) );
        System.out.println(dude1.map(Dude::getName));       // map leaves nesting of optionals - Optional[Optional[Bret]]
        System.out.println(dude1.flatMap(Dude::getName));   // flatmap unwraps the nested optional - Optional[Bret]

        // or
        Optional<String> o1 = Optional.of("value");
        Optional<String> o2 = Optional.empty();
        o1 = o1.or(() -> Optional.of("default"));
        System.out.println(o1); //Optional[value]
        o2 = o2.or(() -> Optional.of("default"));
        System.out.println(o2); //Optional[default]

        Optional<Person> don = Optional.of( new Person("Don", "Biggs", LocalDate.ofYearDay(1930, 48)) );
        Optional<Person> mrNobody = Optional.empty();
        Optional<Person> whoAmI = don.or(Java8Optional::mrEveryMan);
        System.out.printf("I am %s%n", whoAmI.get().getName());
        whoAmI = mrNobody.or(Java8Optional::mrEveryMan);
        System.out.printf("I am %s%n", whoAmI.get().getName());
    }

    static Optional<Person> mrEveryMan() {
        return Optional.of( new Person("John", "Smith", LocalDate.now()) );
    }

    // dumb class to prove point about flatMap
    static class Dude{
        private final String name;

        public Dude( String n ) {
            name = n;
        }

        public Optional<String> getName() {
            return Optional.of( name );
        }
    }

    // map, filter & isPresent
    static boolean isPensioner( final Person person ) {
        return Optional.of( person )
            .map(Person::getAge)
            .filter(a -> a > 65)
            .isPresent();
    }

    // orElseThrow
    static void throwMeSomething() {
        Optional.ofNullable( null ).orElseThrow( UnsupportedOperationException::new );
    }

    // what might be slowing your app down
    static String expensiveDbCall() {
        System.out.println( "Getting 1 million rows from ancient DB server" );
        return "1 million rows";
    }
}
