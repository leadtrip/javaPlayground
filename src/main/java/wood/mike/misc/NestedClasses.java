package wood.mike.misc;

public class NestedClasses {

    private final String notStaticOuterString = "Hello";
    private static final Integer staticOuterInteger = 100;
    public static void main(String[] args) {
        var nc = new NestedClasses();

        NestedClasses.NonStaticNested nonStaticNestedObject = nc.new NonStaticNested();     // need instance of outer class to instantiate the non-static nested class
        nonStaticNestedObject.accessMembers();

        StaticNested staticNestedObject = new StaticNested();       // we can instantiate a static nested class like normal
        staticNestedObject.accessMembers(nc);
    }

    /**
     * Non-static nested class
     */
    class NonStaticNested{
        void accessMembers() {
            System.out.println(notStaticOuterString);
            System.out.println(staticOuterInteger);
        }
    }

    /**
     * Static nested class
     */
    static class StaticNested{
        void accessMembers(NestedClasses nc) {
            // System.out.println(notStaticOuterString);         can't do this from static nested class
            System.out.println(nc.notStaticOuterString);      // need an instance of the outer class to access the non-static members of outer class
            System.out.println(staticOuterInteger);
        }
    }
}
