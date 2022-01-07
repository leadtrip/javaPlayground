package wood.mike.functional.collections;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Imperative and functional styles of finding the first element in a list that matches some criteria
 */
public class PickingAnElement {

    public static void main(String[] args) {
        final List<String> friends = Arrays.asList("Brian", "Nate", "Neal", "Raju", "Sara", "Scott");
        pickNameImperative( friends, "N" );
        pickNameImperative( friends, "Z" );

        pickNameFunctional( friends, "N" );
        pickNameFunctional( friends, "Z" );

        pickNameFunctional2( friends, "N" );
        pickNameFunctional2( friends, "Z" );
    }

    public static void pickNameImperative( final List<String> names, final String startingLetter) {
        String foundName = null;                // mutable variable
        for(String name : names) {                  // have to do the iteration
            if(name.startsWith(startingLetter)) {
                foundName = name;
                break;                              // have to handle breaking out of iteration
            }
        }
        System.out.print(String.format("A name starting with %s: ", startingLetter));
        if(foundName != null) {                         // have to handle possibility of null
            System.out.println(foundName);
        } else {
            System.out.println("No name found");
        }
    }

    public static void pickNameFunctional( final List<String> names, final String startingLetter) {
        final Optional<String> foundName =
                names.stream()
                    .filter(name -> name.startsWith(startingLetter))        // filtering handled
                    .findFirst();                                           // finding match handled

        // using Optional gives us ability to gracefully handle possibility of null
        System.out.println(String.format("A name starting with %s: %s", startingLetter, foundName.orElse("No name found")));
    }


    public static void pickNameFunctional2( final List<String> names, final String startingLetter) {
        names.stream()
            .filter(name -> name.startsWith(startingLetter))
            .findFirst()
            .ifPresent(System.out::println);        // this time only print if something is found, no need for Optional
    }
}
