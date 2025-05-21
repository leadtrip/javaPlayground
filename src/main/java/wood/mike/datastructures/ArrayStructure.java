package wood.mike.datastructures;

import java.util.HashSet;

/**
 * An array is a fixed-size, indexed collection of elements of the same type.
 * ✅ Key Characteristics:
 * Stored in contiguous memory
 * Indexed access (arr[i]) is O(1) — very fast
 * Size is fixed at creation
 * Insertion/deletion (except at the end) is slow — usually O(n)
 */
public class ArrayStructure {

    public static void main(String[] args) {
        int[] numbers = {12, 45, 7, 89, 23};
        int max = findMax(numbers);
        System.out.println(STR."Max value: \{max}");    // should be 89

        int[] numbers1 = {5, 12, 3, 7, 25, 18};
        int result = findSecondLargest(numbers1);
        System.out.println(STR."Second largest: \{result}"); // should be 18

        int[] nums1 = {1, 2, 3, 4, 5};        // false
        int[] nums2 = {1, 2, 3, 2, 5};        // true
        System.out.println(STR."Duplicates? \{hasDuplicates(nums1)}");
        System.out.println(STR."Duplicates? \{hasDuplicates(nums2)}");
    }

    /* O(n) time complexity */
    public static int findMax(int[] arr) {
        int max = Integer.MIN_VALUE;
        for (int j : arr) {
            if (j > max) {
                max = j;
            }
        }
        return max;
    }

    /* O(n) time complexity */
    public static int findSecondLargest(int[] arr) {
        int max = Integer.MIN_VALUE;
        int secondLargest = Integer.MIN_VALUE;
        for(int i: arr) {
            if (i > max) {
                secondLargest = max;
                max = i;
            } else if(i > secondLargest && i < max) {
                secondLargest = i;
            }
        }
        return secondLargest;
    }

    // O(n) time complexity
    public static boolean hasDuplicates(int[] arr) {
        HashSet<Integer> set = new HashSet<>();
        for(int i: arr) {
            if(set.contains(i)) {
                return true;
            }
            set.add(i);
        }
        return false;
    }
}
