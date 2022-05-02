package wood.mike.functional;

import java.util.stream.IntStream;

public class MoreStreams {
    public static void main(String[] args) {
        avgSumCount();
    }

    /**
     * Highlighting differences between average, sum and count on IntStream
     * IntStream.range(x,y) <- from value is inclusive, to value is exclusive so in all cases below we're only considering 4 values
     */
    private static void avgSumCount() {
        var is = IntStream.range(1, 5);
        var avg = is.filter(i -> i % 5 == 0).average();     // none of the 4 values are divisible by 5, OptionalDouble.empty returned as no average can be computed

        is = IntStream.range(1, 5);
        var sum = is.filter(i -> i % 2 != 0).sum();                 // 1 and 3 are not divisible by 2 so answer = 4

        is = IntStream.range(1, 5);
        var count = is.filter(i-> i % 5 == 0).count();              // same as first filter, no filter hits so answer = 0

        System.out.print(avg + " " + sum + " " + count);
    }
}
