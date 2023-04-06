package wood.mike.anything;

import java.util.LinkedHashMap;
import java.util.Map;

public class Anything {
    public static void main(String[] args) {
        Map<String, String> myMap = new LinkedHashMap<>();
        myMap.put("one",  "one.two");
        myMap.put("two",  "one.two");
        myMap.put("three",  "three");
        myMap.put("four",  "four.five");

        myMap.values().stream().filter(ent->ent.contains(".")).distinct().forEach(System.out::println);
    }
}
