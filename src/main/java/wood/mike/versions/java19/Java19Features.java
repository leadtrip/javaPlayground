package wood.mike.versions.java19;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class Java19Features {

    public static void main(String[] args) {
        Java19Features java19Features = new Java19Features();
        java19Features.recordPatternMatching();
    }

    private void recordPatternMatching() {
        Point p = new Point(10, 20);
        printSumOldStyle(p);
        printSumWithRecordPatternMatching(p);
        virtualThreads();
    }

    record Point(int x, int y) {}

    void printSumOldStyle(Object o) {
        if (o instanceof Point p) {
            int x = p.x();              // we have to manually deconstruct the record values
            int y = p.y();
            System.out.println(x+y);
        }
    }

    void printSumWithRecordPatternMatching(Object o) {
        if (o instanceof Point(int x, int y)) {
            System.out.println(x+y);                // the record values are deconstructed and are automatically available
        }
    }

    /**
     * A virtual thread is an instance of java.lang.Thread that runs Java code on an underlying OS thread but does not
     * capture the OS thread for the code's entire lifetime.
     * This means that many virtual threads can run their Java code on the same OS thread, effectively sharing it.
     * While a platform thread monopolizes a precious OS thread, a virtual thread does not.
     * The number of virtual threads can be much larger than the number of OS threads.
     */
    private void virtualThreads() {
        withVirtualThreads(10_000);
        withPlatformThreads(100);       // it takes longer to get 100 old style platform threads going as it does 10000 virtual threads
    }

    private void withVirtualThreads(Integer howMany) {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            IntStream.range(0, howMany).forEach(i -> {
                executor.submit(() -> {
                    Thread.sleep(Duration.ofSeconds(1));
                    return i;
                });
            });
        }
        System.out.println("Virtual threads done");
    }

    private void withPlatformThreads(Integer howMany) {
        try (var executor = Executors.newFixedThreadPool(20)) {
            IntStream.range(0, howMany).forEach(i -> {
                executor.submit(() -> {
                    Thread.sleep(Duration.ofSeconds(1));
                    return i;
                });
            });
        }
        System.out.println("Platform threads done");
    }
}
