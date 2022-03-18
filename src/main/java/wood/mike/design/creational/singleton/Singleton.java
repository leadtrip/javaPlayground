package wood.mike.design.creational.singleton;

/**
 * Lots of variations of Singleton implementations, two of the recommended approaches are the static inner class and enum
 */
public class Singleton {
    public static void main(String[] args) {
        BpSingleton.getInstance().doIt();
        EnumSingleton.INSTANCE.doIt();
    }
}

// Bill Pugh singleton
class BpSingleton {

    // private
    private BpSingleton() {}

    // static inner class - inner classes are not loaded until they are referenced
    private static class MySingletonHelper {
        private static final BpSingleton INSTANCE = new BpSingleton();
    }

    public static BpSingleton getInstance() {
        return MySingletonHelper.INSTANCE;
    }

    public void doIt() {
        System.out.println("Doing it");
    }
}

/**
 * Enum provides implicit support for thread safety and only one instance is guaranteed
 */
enum EnumSingleton {
    INSTANCE;
    public void doIt() {
        System.out.println("Doing it");
    }
}