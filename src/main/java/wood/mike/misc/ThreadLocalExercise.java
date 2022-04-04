package wood.mike.misc;

import wood.mike.helper.Person;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Playing around with ThreadLocal, not typical usage but demonstrates features
 */
public class ThreadLocalExercise {

    public static void main(String[] args) throws Exception {

        // This ThreadLocal is defined in the main thread and accessed in the 2 threads below both of which set their own values
        ThreadLocal<String> threadLocal = new ThreadLocal<>();

        Thread thread1 = new Thread(() -> {
            threadLocal.set("thread1");
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }

            String value = threadLocal.get();
            System.out.println(value);

            threadLocal.remove();

            System.out.println(threadLocal.get());
        });

        Runnable r = () -> {
            threadLocal.set("thread2");
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }

            String value = threadLocal.get();
            System.out.println(value);
        };

        thread1.start();
        new Thread(r).start();

        //--------------------------------------------------------------

        // use static factory to provide initial value, again this ThreadLocal is defined in main thread with 2 x threads
        // below setting values as does the main thread
        ThreadLocal<Person> threadLocalPerson = ThreadLocal.withInitial(
                () -> new Person.PersonBuilder("Mr", "Nobody", "2000-01-01" ).build()
        );

        Thread thread3 = new Thread(()-> {
            Person p = threadLocalPerson.get();
            p.setDod(LocalDate.now());
            System.out.println(threadLocalPerson.get());
            try {
                Thread.sleep(500 );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            p.setDod(null);
            System.out.println(threadLocalPerson.get());
        });

        Thread thread4 = new Thread(()-> {
            Person p = threadLocalPerson.get();
            p.setDod(LocalDate.now().minusYears(2).minusMonths( 2 ).minusDays( 3 ));
            System.out.println(threadLocalPerson.get());
            try {
                Thread.sleep(1000 );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(threadLocalPerson.get());
        });

        thread3.start();
        thread4.start();

        Person mainThreadPerson = threadLocalPerson.get();
        mainThreadPerson.setDod( LocalDate.now().minusYears( 17 ) );
        System.out.println(mainThreadPerson);

        Thread.sleep(3000);

        // -----------------------------------------------

        // DemoThread stores instances of 2 ThreadLocals
        new Thread(new DemoTask()).start();
        Thread.sleep(20);
        new Thread(new DemoTask()).start();
        Thread.sleep(20);
        new Thread(new DemoTask()).start();
    }

}

class DemoTask implements Runnable
{
    // Atomic integer containing the next thread ID to be assigned
    private static final AtomicInteger nextId   = new AtomicInteger(0);

    // Thread local variable containing each thread's ID
    private static final ThreadLocal<Integer> threadId = ThreadLocal.withInitial(nextId::getAndIncrement);

    // Returns the current thread's unique ID, assigning it if necessary
    public int getThreadId()
    {
        return threadId.get();
    }
    // Returns the current thread's starting timestamp
    private static final ThreadLocal<LocalTime> startDate = ThreadLocal.withInitial(LocalTime::now);

    @Override
    public void run()
    {
        System.out.printf("Starting Thread: %s : %s\n", getThreadId(), startDate.get());
        try
        {
            TimeUnit.SECONDS.sleep((int) Math.rint(Math.random() * 10));
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        System.out.printf("Thread Finished: %s : %s\n", getThreadId(), startDate.get());
    }
}
