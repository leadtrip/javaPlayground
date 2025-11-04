package wood.mike.anything;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Value objects:
 * Defined only by their data.
 * If two value objects contain the same data, they are equal.
 * They are immutable by design (good practice).
 * Equality is usually implemented via equals() and hashCode()

 * Identity objects:
 * Defined by a unique identity that persists across time/changes.
 * Two objects can have the same state/data but are still different if they have different identity.
 * Equality is usually reference equality (==), or sometimes by an explicit identifier (e.g. database primary key).
 * Typically mutable.
 */
public class IdentityAndValue {
    static void main() {
        IdentityAndValue identityAndValue = new IdentityAndValue();
        identityAndValue.identityCollections();
        identityAndValue.valueCollections();
        identityAndValue.valueData();
    }

    /**
     * All of these collections can be modified in some way, they are identity based
     */
    private void identityCollections() {
        List<String> il1 = new ArrayList<>();
        il1.add("i1");
        List<String> il2 = Stream.of("i1", "i2", "i3").collect(Collectors.toList());
        il2.add("i4");
        List<String> il3 = Arrays.asList("i1", "i2", "i3");
        il3.set(0, "i0");
    }

    /**
     * None of these collections can be modified, they are value based
     */
    private void valueCollections() {
        List<String> vl1 = List.of("v1", "v2", "v3", "v4", "v5", "v6", "v7");
        // vl1.add("v7");
        List<String> vl2 = Stream.of("v1", "v2", "v3", "v4", "v5", "v6", "v7").toList();
        //vl2.remove("v1");
        List<String> vl3 = Collections.unmodifiableList(List.of("v1", "v2", "v3", "v4", "v5", "v6"));
        //vl3.set(0, "v0");
    }

    private void valueData() {
        new Person(
                "Bob",
                ImmutableList.of("Jane", "Joe")
        );

        new Person(
                null, null
        );
    }
}

record Degrees(double value) {
    Degrees {
        if (!(Double.compare(value, 0.0) >= 0
                && Double.compare(value, 360.0) < 0)) {
            throw new IllegalArgumentException("Invalid angle");
        }
    }
}

record Person(
        String name,
        ImmutableList<String> friends
){}



