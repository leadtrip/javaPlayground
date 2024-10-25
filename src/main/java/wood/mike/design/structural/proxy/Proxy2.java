package wood.mike.design.structural.proxy;

/**
 * Example of proxy wrapping access to heavyweight resource
 * In real world there'd be readers involved but for simplicity they've been omitted
 */
public class Proxy2 {
    public static void main(String[] args) {
        SmartCard smartCard = new ProxySmartCard();
        System.out.println( smartCard.getId() );        // first call accesses reader/card
        System.out.println( smartCard.getId() );        // subsequent calls fetch cached value
    }
}

// A heavy resource
interface SmartCard {
    String getId();
}

// The real heavy resource
class RealSmartCard implements SmartCard {
    @Override
    public String getId() {
        System.out.printf( "%s getting ID%n", getClass().getSimpleName() );
        return "84493800129991";
    }
}

// Proxy wrapping heavy resource providing caching to lighten load after first access
class ProxySmartCard implements SmartCard {
    String id;
    private final SmartCard realSmartCard = new RealSmartCard();

    @Override
    public String getId() {
        if( id == null ) {
            id = realSmartCard.getId();
        }
        return id;
    }
}
