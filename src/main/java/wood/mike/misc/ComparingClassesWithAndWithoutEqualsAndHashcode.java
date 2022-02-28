package wood.mike.misc;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Objects;

/**
 * Playing around with equals and hashcode with regard to various collection implementation operations
 */
public class ComparingClassesWithAndWithoutEqualsAndHashcode {

    public static void main(String[] args) {
        ComparingClassesWithAndWithoutEqualsAndHashcode noEqualsAndHashcode = new ComparingClassesWithAndWithoutEqualsAndHashcode();
        noEqualsAndHashcode.run();
    }

    private void run() {
        testEqualityWith();
        testEqualityWithout();
        testHashMapWith();
        testHashMapWithout();
    }
    
    private void testEqualityWithout() {
        NoEqualsAndHashcode original = new NoEqualsAndHashcode( "original", 1 );
        NoEqualsAndHashcode deepCopy = deepCopy(NoEqualsAndHashcode.class, original);

        assert !original.equals(deepCopy);      // with no equals implementation defaults to reference equality
        assert original != deepCopy;            // clearly not the same reference

        assert original.hashCode() != deepCopy.hashCode();  // default implementation of hashcode results in difference
    }

    private void testEqualityWith() {
        HasEqualsAndHashcode original = new HasEqualsAndHashcode( "original", 1 );
        HasEqualsAndHashcode deepCopy = deepCopy(HasEqualsAndHashcode.class, original);

        assert original.equals(deepCopy);      // objects are equals according to our defined equals method
        assert original != deepCopy;           // but not the same reference

        assert original.hashCode() == deepCopy.hashCode();  // and hashcode is same
    }
    
    private void testHashMapWithout() {
        // first off test class with no equals or hashcode defined
        NoEqualsAndHashcode original = new NoEqualsAndHashcode("original", 1);
        NoEqualsAndHashcode deepCopy = deepCopy(NoEqualsAndHashcode.class, original);

        HashMap<NoEqualsAndHashcode, String> map = new HashMap<>();
        map.put(original, "original");
        map.put(deepCopy, "deepCopy");      // not a problem, both objects accepted

        assert map.size() == 2;
    }

    private void testHashMapWith() {
        HasEqualsAndHashcode original = new HasEqualsAndHashcode( "original", 1 );
        HasEqualsAndHashcode deepCopy = deepCopy(HasEqualsAndHashcode.class, original);

        HashMap<HasEqualsAndHashcode, String> mapWith = new HashMap<>();
        mapWith.put(original, "original");
        mapWith.put(deepCopy, "deepCopy");      // overwrite original

        assert mapWith.size() == 1;

        assert !mapWith.containsValue("original");
        assert mapWith.containsValue("deepCopy");
    }

    /**
     * @return a copy of the supplied object
     */
    private <T> T deepCopy( Class<T> theClass, T theObject ) {
        Gson gson = new Gson();
        return gson.fromJson( gson.toJson( theObject ), theClass );
    }

    /**
     * Simple class with a couple of fields and no equals or hashcode so reliant on default
     */
    private static class NoEqualsAndHashcode {
        String name;
        Integer sku;

        public NoEqualsAndHashcode(String name, Integer sku) {
            this.name = name;
            this.sku = sku;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setSku(Integer sku) {
            this.sku = sku;
        }
    }

    /**
     * Simple class with a couple of fields with equals and hashcode created by intellij
     */
    private static class HasEqualsAndHashcode {
        String name;
        Integer sku;

        public HasEqualsAndHashcode(String name, Integer sku) {
            this.name = name;
            this.sku = sku;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setSku(Integer sku) {
            this.sku = sku;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            HasEqualsAndHashcode that = (HasEqualsAndHashcode) o;
            return name.equals(that.name) && sku.equals(that.sku);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, sku);
        }
    }
}
