package wood.mike.guava;

import com.google.common.base.Optional;

import java.util.List;
import static com.google.common.base.Preconditions.*;


public class GoogleGuava {

    public static void main(String[] args) {
        var gg = new GoogleGuava();
        gg.optional();
        gg.preConditions((double) 1);
    }

    private void preConditions(Double dbVal) {
        checkNotNull(dbVal, "Input is null %s", dbVal);
        checkArgument(dbVal >= 0, "Input is negative: $s", dbVal, "");

        List<Double> dblList = List.of(dbVal);
        checkElementIndex(0, dblList.size());
    }

    private void optional() {
        List<Optional<String>> strList =
                List.of(
                        Optional.of("wheel"),
                        Optional.of("tyre"),
                        Optional.absent(),
                        Optional.of("frame"),
                        Optional.absent());

        strList.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .peek(System.out::println)
                .toList();
    }
}
