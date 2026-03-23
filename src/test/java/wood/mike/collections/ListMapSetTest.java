package wood.mike.collections;

import org.junit.jupiter.api.Test;

import java.security.KeyStore;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ListMapSetTest {

    /**
     * Lists are:
     *  ordered
     *  indexed
     *  duplicates allowed
     * Use a list when:
     *  You need fast reads
     *  You mostly append
     *  You rarely insert/remove from the middle
     */
    @Test
    public void listStuff() {
        List<String> list = new ArrayList<>();

        // adding
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("B");    // duplicates allowed
        assertEquals(4, list.size());
        assertEquals(2, list.stream().filter(i -> i.equals("B")).toList().size());
        assertEquals("A", list.get(0));     // the same as getFirst
        list.addFirst("Z");
        assertEquals("Z",list.getFirst());
        list.addLast("D");
        assertEquals("D", list.get(list.size() - 1));   // the same as getLast

        // finding things
        assertFalse(list.contains("P"));
        assertEquals(-1, list.indexOf("R"));
        Optional<String> first = list.stream().filter(i -> i.equalsIgnoreCase("a")).findFirst();
        assertTrue(first.isPresent());
        assertEquals("A", first.get());

        // iterate
        int totalbs = 0;
        for(String i : list) {
            if(i.equals("B")) {
                totalbs ++;
            }
        }
        assertEquals(2, totalbs);

        for(int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }

        // removing
        list.remove(1);
        list.remove("a");
    }

    /**
     * Use a set when:
     *  You need fast contains() checks
     *  You want to prevent duplicates
     *  Order doesn’t matter
     */
    @Test
    public void setStuff() {
        Set<String> set = new HashSet<>();

        // adding
        set.add("A");
        set.add("B");
        set.add("C");
        set.add("B");   // ignored, no duplicates
        assertEquals(3, set.size());

        // finding
        assertTrue(set.contains("A"));

        // iterating
        for(String i : set) {
            System.out.println(i);
        }

        // removing
        set.remove("A");
        assertFalse(set.contains("A"));
    }

    /**
     * Use a map when:
     *  You need fast lookups by key
     *  Order does not matter
     */
    @Test
    public void mapStuff() {
        Map<String, Integer> map = new HashMap<>();

        // adding
        map.put("A", 1);
        map.put("B", 2);
        map.put("C", 3);
        map.put("B", 4);    // no duplicate keys
        assertEquals(3, map.size());

        // finding
        assertEquals(1, map.get("A"));
        assertTrue(map.containsKey("B"));
        assertTrue(map.containsValue(1));

        // iterating
        for(String key : map.keySet()) {
            System.out.println(key);
        }

        for(Integer i : map.values()) {
            System.out.println(i);
        }

        for(Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(STR."\{entry.getKey()} -> \{entry.getValue()}");
        }

        map.forEach((key, value) -> System.out.println(key));

        // removing
        map.remove("A");
        assertFalse(map.containsKey("A"));

        // replace
        map.replace("B", 99);
        assertTrue(map.containsValue(99));
    }
}
