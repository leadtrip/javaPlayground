package wood.mike.concurrency.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;

public class SumArrayTaskRunner {

    public static void main(String[] args) {
        long[] numbers = generateRandomLongArray(10_000_000_00);

        ForkJoinPool pool = ForkJoinPool.commonPool();

        long startTime = System.currentTimeMillis();

        SumArrayTask mainTask = new SumArrayTask(numbers, 0, numbers.length);

        long totalSum = pool.invoke(mainTask); // invoke() waits for the task to complete

        long endTime = System.currentTimeMillis();

        System.out.println(STR."Total sum: \{totalSum}");
        System.out.println(STR."Time taken: \{endTime - startTime} ms");

        // A sequential sum for comparison
        long sequentialStartTime = System.currentTimeMillis();
        long sequentialSum = 0;
        for (long num : numbers) {
            sequentialSum += num;
        }
        long sequentialEndTime = System.currentTimeMillis();
        System.out.println(STR."Sequential sum: \{sequentialSum}");
        System.out.println(STR."Sequential time taken: \{sequentialEndTime - sequentialStartTime} ms");
    }

    private static long[] generateRandomLongArray(int size) {
        long[] array = new long[size];
        for (int i = 0; i < size; i++) {
            array[i] = ThreadLocalRandom.current().nextLong(100);
        }
        return array;
    }
}
