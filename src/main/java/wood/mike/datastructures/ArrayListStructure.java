package wood.mike.datastructures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * An ArrayList is a resizable array — it automatically grows or shrinks as needed. It’s part of java.util.
 * ✅ Key Features:
 * Dynamic size — no need to declare the number of elements in advance
 * Internally backed by an array
 * Provides built-in methods like add(), remove(), contains(), size(), etc.
 */
public class ArrayListStructure {
    public static void main(String[] args) {
        List<Integer> nums = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        removeEvens(nums);
        System.out.println(STR."After removing evens: \{nums}"); // [1, 3, 5, 7, 9]

        List<Integer> nums1 = new ArrayList<>(Arrays.asList(5, 3, 7, 3, 9, 3));
        List<Integer> indices = findAllIndices(nums1, 3);
        System.out.println(STR."Indices of 3: \{indices}"); // should print [1, 3, 5]

        List<Integer> nums2 = Arrays.asList(1, 2, 3, 5, 6, 9, 10, 11, 12);
        List<List<Integer>> result = groupConsecutives(nums2);
        System.out.println(result); // [[1, 2, 3], [5, 6], [9, 10, 11, 12]]
    }

    /**
     * Time complexity: O(n) — scan each element once.
     * Space complexity: O(1) — modifying the list in place, without allocating extra memory.
     */
    public static void removeEvens(List<Integer> numbers) {
        Iterator<Integer> iterator = numbers.iterator();
        while (iterator.hasNext()) {
            if (iterator.next() % 2 == 0) {
                iterator.remove();
            }
        }
    }

    /**
     * Time: O(n) — one full pass through the list
     * Space: O(k) — where k is the number of times the target appears (for storing indices)
     */
    public static List<Integer> findAllIndices(List<Integer> list, int target) {
        List<Integer> result = new ArrayList<>();
        int idx = 0;
        for(int i: list) {
            if (i == target) {
                result.add(idx);
            }
            idx++;
        }
        return result;
    }

    /**
     * Time: O(n)
     * Space: O(n) for storing the grouped sublists
     */
    public static List<List<Integer>> groupConsecutives(List<Integer> numbers) {
        ArrayList<List<Integer>> result = new ArrayList<>();
        for (Integer number : numbers) {
            if (!result.isEmpty() && number == result.getLast().getLast() + 1) {
                result.getLast().add(number);
            } else {
                ArrayList<Integer> newCurrent = new ArrayList<>();
                newCurrent.add(number);
                result.add(newCurrent);
            }
        }
        return result;
    }
}
