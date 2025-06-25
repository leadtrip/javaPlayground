package wood.mike.concurrency.forkjoin;

import java.util.concurrent.RecursiveTask;

public class SumArrayTask extends RecursiveTask<Long> {

    private final long[] array;
    private final int start;
    private final int end;
    private static final int THRESHOLD = 1000;

    public SumArrayTask(long[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        System.out.println(STR."\{end - start}");
        // base case, there's no point in spinning off new threads when there's fewer that THRESHOLD numbers in the array
        if (end - start <= THRESHOLD) {
            long sum = 0;
            for (int i = start; i < end; i++) {
                sum += array[i];
            }
            return sum;
        } else {
            // Recursive case: Fork into two subtasks
            int mid = start + (end - start) / 2; // Calculate the midpoint

            SumArrayTask leftTask = new SumArrayTask(array, start, mid);
            SumArrayTask rightTask = new SumArrayTask(array, mid, end);

            // Fork the right task to run asynchronously
            rightTask.fork(); // This schedules the rightTask for execution in the pool

            // Compute the left task directly on the current thread
            // This is a common optimization to keep the current thread busy
            Long leftResult = leftTask.compute();

            // Join the right task to get its result (blocks until it's done)
            Long rightResult = rightTask.join();

            // Combine the results
            return leftResult + rightResult;
        }
    }
}
