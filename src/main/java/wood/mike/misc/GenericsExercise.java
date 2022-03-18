package wood.mike.misc;

import com.google.common.util.concurrent.AtomicDouble;
import lombok.ToString;
import wood.mike.helper.Person;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

import static java.util.List.of;

/**
 * Playing around with generics
 */
public class GenericsExercise {

    public static void main(String[] args) {

        List<Xob<String>> xobs = xobs(of("Welcome", "To", "The", "Jungle"));

        Xob<String> xob1 = getFirst( xobs );
        System.out.println( xob1 );

        printer(xob1);

        List<String> strings = convertTo(xobs, Xob::getContents);
        System.out.println(String.join(" ", strings ) );

        Function<Xob<String>, Person> xobPersonFunction = xob -> new Person.PersonBuilder("Ned", xob.contents, "2022-03-17" ).build();
        List<Person> aLoadOfNeds = convertTo( xobs( of("Sweet", "child", "o'mine" ) ), xobPersonFunction );
        aLoadOfNeds.forEach(System.out::println);

        List<Xob<UUID>> uuidXobs = xobs(of(UUID.randomUUID(), UUID.randomUUID()));

        Xob<Integer> integerXob = numberXob(2);
        Xob<Long> longXob = numberXob(2L);
        Xob<Double> doubleXob = numberXob(2.5);
        Xob<AtomicInteger> atomicIntegerXob = numberXob(new AtomicInteger(2));
        Xob<Byte> byteXob = numberXob((byte) 3);
        Xob<Float> floatXob = numberXob(22.2F);
        Xob<?> wildCardXob = numberXob(0b00110);
        Xob<Long> hexXob = numberXob(0xFF_EE_DD_CC_BB_AAL);
        Xob<BigInteger> bigIntegerXob = numberXob(BigInteger.ONE);

        numberXobSum( of( integerXob ) );       // this is fine & any number more of Integer type Xobs
        //numberXobSum( of( integerXob, longXob ) );  // but can't mix & types

        // have to use Number as Xob type parameter to allow mixing of contents
        Xob<Number> nuXob1 = numberXob( 124 );
        Xob<Number> nuXob2 = numberXob( 125.5 );
        Xob<Number> nuXob3 = numberXob( (short) '~' );
        Xob<Number> nuXob4 = numberXob( (byte) 127 );
        Xob<Number> nuXob5 = numberXob( new AtomicDouble(128));
        Long nuXobSum = numberXobSum( of( nuXob1, nuXob2, nuXob3, nuXob4, nuXob5 ) );
        System.out.printf("Number Xob sum %s%n", nuXobSum );

        Long integerXobSum = numberXobSum(of(numberXob(1), numberXob(3), numberXob(-4)));
        Long floatXobSum = numberXobSum(of(numberXob(2.4), numberXob(993.12), numberXob(9.11)));
        Long longXobSum = numberXobSum(of(numberXob(29108320130120L), numberXob(2893812038120381L)));
        Long atomicLongXobSum = numberXobSum(of(numberXob(new AtomicLong(901830218)), numberXob(new AtomicLong(9898))));

        System.out.printf( "Integer Xob sum %s%n", integerXobSum );
        System.out.printf( "Float Xob sum %s%n", floatXobSum );
        System.out.printf( "Long Xob sum %s%n", longXobSum );
        System.out.printf( "Atomic long Xob sum %s%n", atomicLongXobSum );
    }

    private static <T> List<Xob<T>> xobs( List<T> contents ) {
        return contents.stream()
                .map(Xob::new)
                .toList();
    }

    private static <T> T getFirst( List<T> list ) {
        return list.get(0);
    }

    private static <T> void printer( T a ) {
        System.out.println( a );
    }

    private static <T, G> List<G> convertTo(List<T> list, Function<T, G> function ) {
        return list.stream()
                .map(function)
                .toList();
    }

    private static <T extends Number> Xob<T> numberXob( T num ) {
        return new Xob<T>(num);
    }

    private static <T extends Number> Long numberXobSum( List<Xob<T>> numberXobs ) {
        return numberXobs.stream()
                .mapToLong( xob -> xob.getContents().longValue() )
                .peek(System.out::println)
                .sum();
    }

    /**
     * T is explicitly specified and U is inferred
     */
    private static void genericConstructor() {
        Pleb<Integer> p1 = new Pleb<>(1, "hey");
        Pleb<String> p2 = new Pleb<>("yo", 2);
    }
}

@ToString
class Xob<T> {
    T contents;

    Xob( T c ) {
        contents = c;
    }

    T getContents() {
        return contents;
    }
}

class Pleb<T> {
    T t;
    <U> Pleb( T ttt, U uuu ) {
        // constructors can declare their own formal type parameters
    }
}
