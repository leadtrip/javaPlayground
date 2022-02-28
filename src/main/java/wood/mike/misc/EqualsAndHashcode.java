package wood.mike.misc;

import com.google.gson.Gson;

import java.util.*;

/**
 * Playing around with equals and hashcode with regard to various collection implementation operations
 */
public class EqualsAndHashcode {

    public static void main(String[] args) {
        EqualsAndHashcode equalsAndHashcode = new EqualsAndHashcode();
        equalsAndHashcode.run();
    }

    private void run() {
        testEquality();
        testHashMap();
        testHashSet();
        testUnmodifiableSet();
    }

    private void testHashMap() {
        System.out.println("----------- testHashMap -----------");
        var original = lof();
        var deepCopy = deepCopy(original);

        Map<LotsOfFields, String> lofMap = new HashMap<>();
        System.out.println("Adding original");
        lofMap.put(original, "original");

        assert lofMap.size() == 1;
        assert lofMap.containsValue("original");

        System.out.println("Adding deep copy");
        lofMap.put(deepCopy, "deepCopy");

        assert lofMap.size() == 1;
        assert lofMap.containsValue("deepCopy");    // copy replaced original

        System.out.println("Modifying original");
        original.aBoolean = false;

        System.out.println("Adding modified original");
        lofMap.put(original, "original");

        // deep copy was modified and is now accepted into map
        assert lofMap.size() == 2;
        assert lofMap.values().containsAll(List.of("original", "deepCopy"));

        System.out.println("Reverting change to original");
        original.aBoolean = true;

        System.out.println("hashcode for each map entry");
        lofMap.entrySet().forEach(Map.Entry::hashCode);     // now original and deep copy are same again

        System.out.println("Getting original from map");
        String originalFromMap = lofMap.get(original);      // get the original from map
        assert !"original".equals(originalFromMap);         // original from map is not the same object by reference in map
        assert "deepCopy".equals(originalFromMap);          // it's the deep copy

        System.out.println("Getting deep copy from map");
        String deepCopyFromMap = lofMap.get(deepCopy);
        assert !"original".equals( deepCopyFromMap );
        assert "deepCopy".equals( deepCopyFromMap );


        System.out.println("Creating list of map keys");
        List<LotsOfFields> mapKeys = new ArrayList<>(lofMap.keySet());
        assert mapKeys.size() == 2;
        LotsOfFields lofFromList1 = mapKeys.get(0);
        LotsOfFields lofFromList2 = mapKeys.get(1);

        assert lofFromList1 == lofFromList2;            //  both keys are the same reference

        System.out.println("Creating list of values");
        List<String> mapValues = new ArrayList<>(lofMap.values());
        String value1 = mapValues.get(0);
        String value2 = mapValues.get(1);

        assert !value1.equals(value2);                  // map values are different

        System.out.println("Removing original from map");
        lofMap.remove(original);

        assert lofMap.size() == 1;                      // map contains single item
        assert lofMap.get(original) == null;            // but can't access it with original
        assert lofMap.get(deepCopy) == null;            // or the copy
        lofMap.entrySet().forEach(System.out::println); // but it's there
    }

    private void testUnmodifiableSet() {
        System.out.println("----------- testUnmodifiableSet -----------");
        LotsOfFields original = lof();
        LotsOfFields deepCopy = deepCopy( original );

        // Trying to create set with Set.of... without modifying one instance will throw IllegalArgumentException - duplicate element
        deepCopy.anInteger--;

        System.out.println("Creating set");
        Set<LotsOfFields> lotsOfFieldsSet = Set.of(original, deepCopy);

        assert lotsOfFieldsSet.size() == 2;

        assert !original.equals(deepCopy);

        deepCopy.anInteger++;

        assert original.equals(deepCopy);

        lotsOfFieldsSet.forEach(l -> System.out.println(l.hashCode()));
    }

    private void testHashSet() {
        System.out.println("----------- testHashSet -----------");
        LotsOfFields original = lof();
        LotsOfFields deepCopy = deepCopy( original );

        HashSet<LotsOfFields> hs = new HashSet<>();
        System.out.println("Adding original");
        hs.add(original);
        System.out.println("Adding deep copy");
        hs.add(deepCopy);

        System.out.printf("Set size: %s%n", hs.size() );
        assert hs.size() == 1;

        System.out.println("Checking if set contains deep copy...");
        boolean containsDeepCopy = hs.contains(deepCopy);
        System.out.printf( "Set contains deep copy: %s%n", containsDeepCopy );

        System.out.println("Modifying deep copy");
        deepCopy.anInteger++;

        System.out.println("Adding modified deep copy");
        hs.add(deepCopy);

        System.out.printf("Set size: %s%n", hs.size() );
        assert hs.size() == 2;

        System.out.println("Checking hashcode on all set entries");
        hs.forEach(LotsOfFields::hashCode);

        System.out.println("Reverting change to deep copy");
        deepCopy.anInteger--;

        System.out.println("Checking hashcode on all set entries");
        hs.forEach(LotsOfFields::hashCode);

        System.out.println("Checking if set contains original...");
        boolean containsOriginal = hs.contains(original);
        System.out.printf( "Set contains original: %s%n", containsOriginal );

        System.out.println("Checking if set contains deep copy...");
        boolean containsDeepCopy1 =  hs.contains(deepCopy);
        System.out.printf( "Set contains deep copy: %s%n", containsDeepCopy1 );
    }

    private void testEquality() {
        System.out.println("----------- testEquality -----------");
        LotsOfFields original = lof();
        LotsOfFields deepCopy = deepCopy( original );

        assert original.hashCode() == deepCopy.hashCode();
        assert original.equals( deepCopy );
        assert original != deepCopy;
    }

    /**
     * @return  a pre-populated instance of test class
     */
    private LotsOfFields lof() {
        LotsOfFields lotsOfFields = new LotsOfFields();
        lotsOfFields.aBoolean = Boolean.TRUE;
        lotsOfFields.aLong = 29183021L;
        lotsOfFields.aMap = Map.of("aKey", "aValue");
        lotsOfFields.anInt = 321;
        lotsOfFields.anInteger = 9;
        lotsOfFields.aString = "toilet";
        lotsOfFields.integerList = List.of(9213, 488);
        return lotsOfFields;
    }

    /**
     * @param lotsOfFields  - the instance to copy
     * @return a deep copy of the supplied instance
     */
    private LotsOfFields deepCopy( LotsOfFields lotsOfFields ) {
        Gson gson = new Gson();
        return gson.fromJson( gson.toJson( lotsOfFields ), LotsOfFields.class );
    }

    /**
     * Class with a number of different fields used for testing
     */
    private static class LotsOfFields {
        private String aString;
        private Integer anInteger;
        private Long aLong;
        private List<Integer> integerList;
        private Map<String, Object> aMap;
        private Boolean aBoolean;
        private int anInt;

        public String getaString() {
            return aString;
        }

        public void setaString(String aString) {
            this.aString = aString;
        }

        public Integer getAnInteger() {
            return anInteger;
        }

        public void setAnInteger(Integer anInteger) {
            this.anInteger = anInteger;
        }

        public Long getaLong() {
            return aLong;
        }

        public void setaLong(Long aLong) {
            this.aLong = aLong;
        }

        public List<Integer> getIntegerList() {
            return integerList;
        }

        public void setIntegerList(List<Integer> integerList) {
            this.integerList = integerList;
        }

        public Map<String, Object> getaMap() {
            return aMap;
        }

        public void setaMap(Map<String, Object> aMap) {
            this.aMap = aMap;
        }

        public Boolean getaBoolean() {
            return aBoolean;
        }

        public void setaBoolean(Boolean aBoolean) {
            this.aBoolean = aBoolean;
        }

        public int getAnInt() {
            return anInt;
        }

        public void setAnInt(int anInt) {
            this.anInt = anInt;
        }

        @Override
        public String toString() {
            return "LotsOfFields{" +
                    "aString='" + aString + '\'' +
                    ", anInteger=" + anInteger +
                    ", aLong=" + aLong +
                    ", integerList=" + integerList +
                    ", aMap=" + aMap +
                    ", aBoolean=" + aBoolean +
                    ", anInt=" + anInt +
                    '}';
        }

        /**
         * Generated by intellij
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            LotsOfFields that = (LotsOfFields) o;
            var eq = Objects.equals(aString, that.aString)
                    && Objects.equals(anInteger, that.anInteger)
                    && Objects.equals(aLong, that.aLong)
                    && Objects.equals(integerList, that.integerList)
                    && Objects.equals(aMap, that.aMap)
                    && Objects.equals(aBoolean, that.aBoolean)
                    && Objects.equals(anInt, that.anInt);
            System.out.printf("equals called, result: %s%n", eq);
            return eq;
        }

        /**
         * Generated by intellij
         */
        @Override
        public int hashCode() {
            int hash = Objects.hash(aString, anInteger, aLong, integerList, aMap, aBoolean, anInt);
            System.out.printf( "hashCode called: %s%n", hash );
            return hash;
        }
    }
}
