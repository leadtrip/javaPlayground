package wood.mike.design.behavioral.chainofresponsibility;

/**
 * Offers ability for multiple classes to interact with an object, zero, one or more could interact with the payload
 */
public class ChainOfResponsibility {
    public static void main(String[] args) {
        Filter filter = new DbFilter();
        filter.addFilter( new LoggingFilter() );
        filter.check("seabass" );
    }
}

// base filter
abstract class Filter {
    private Filter next;

    public Filter addFilter(Filter next) {
        this.next = next;
        return next;
    }

    public abstract void check(String thing);

    protected void checkNext(String thing) {
        if (next != null) {
            next.check(thing);
        }
    }
}

// DB filter, we could of course check if the thing is relevant to us but for demo everything is relevant
class DbFilter extends Filter{
    @Override
    public void check(String thing) {
        System.out.printf("Doing something in the DB with %s%n", thing );
        checkNext( thing );
    }
}

// Logging filter, bit of checking going on this time
class LoggingFilter extends Filter {
    @Override
    public void check(String thing) {
        if ( thing.length() < 20 ) {
            System.out.printf("Logging %s%n", thing);
        }
        checkNext( thing );
    }
}