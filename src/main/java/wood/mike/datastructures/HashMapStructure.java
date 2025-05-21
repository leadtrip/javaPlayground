package wood.mike.datastructures;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
  A HashMap<K, V> in Java stores key-value pairs, where:
    Keys are unique
    Values can be duplicated
    It provides constant-time average performance for put, get, remove, and containsKey — O(1) on average.

  Think of a HashMap like a dictionary:
    You look up a word (key) → get the definition (value).
    You can’t have the same word twice (keys must be unique).
    Different words may have the same meaning (values can repeat).

 📚 When to Use HashMap
    Fast lookups by key
    Counting occurrences (frequency maps)
    Caching
    Mapping relationships (e.g., userID → User object)
 */
public class HashMapStructure {
    public static void main(String[] args) {
        System.out.println(countFrequencies("hello"));

        System.out.println(firstNonRepeating("swiss"));  // w
        System.out.println(firstNonRepeating("aabbcc")); // '\0'
    }


    public static HashMap<Character, Integer> countFrequencies(String input) {
        HashMap<Character, Integer> map = new HashMap<>();
        for (char ch : input.toCharArray()) {
            map.put(ch, map.getOrDefault(ch, 0) + 1);
        }
        return map;
    }

    /**
     * Counting frequency: O(n)
     * Then scanning the string again for the first count==1: O(n)
     * Total O(n) time, and correct order.
     */
    public static char firstNonRepeating(String input) {
        HashMap<Character, Integer> map = new HashMap<>();
        for (char ch : input.toCharArray()) {
            map.put(ch, map.getOrDefault(ch, 0) + 1);
        }
        for (char ch : input.toCharArray()) {
            if (map.get(ch) == 1) {
                return ch;
            }
        }
        return '\0';
    }


}
