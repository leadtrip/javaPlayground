package wood.mike.timeandspacecomplexity;

import java.util.Arrays;
import java.util.HashMap;

public class TimeComplexity {

    /**
     Real world analogies for each time complexity

     O(1): Constant time — execution time doesn't grow with input size.
     Example: Grabbing a specific book from a known shelf — always takes the same time.

     O(n): Linear time — time grows proportionally with input size.
     Example: Scanning a list of names to find someone — more names, more time.

     O(log n): Logarithmic time — input size is repeatedly halved.
     Example: Finding a word in a dictionary by flipping to the middle, then narrowing down.

     O(n²): Quadratic time — performance degrades quickly due to nested loops.
     Example: Comparing every person with every other person in a room — more people, much more time.

     O(n log n): Log-linear time — efficient sorting algorithms.
     Example: Organizing a pile of shuffled papers into alphabetical order using divide-and-conquer.
     */
    public static void main(String[] args) {
        TimeComplexity timeComplexity = new TimeComplexity();
        timeComplexity.constantTime();
        timeComplexity.linearTime();
        timeComplexity.quadraticTime();
        timeComplexity.logarithmicTime();
        timeComplexity.ohNlogN();
    }

    /**
      O(1) or constant time

      An algorithm has O(1) time complexity if its runtime does not depend on the size of the input
      Whether you have 1 item or 1 million, the operation takes roughly the same time.
      You're not looping, scanning, or doing anything that grows with n.

      Describe this verbally as 'this algorithm runs in oh one time' or 'array access is constant time'
     */
    private void constantTime() {
        // Example 1: Accessing an Array Element
        int[] numbers = {10, 20, 30, 40, 50};
        int result = numbers[2]; // Accessing an element directly — O(1)
        System.out.println(STR."Value: \{result}"); // Output: 30

        // Example 2: Assigning a Variable
        int a = 5;      // O(1)
        int b = 10;     // O(1)
        int c = a + b;  // O(1)

        System.out.println(STR."Sum: \{c}");

        // Example 3: HashMap Key Lookup
        HashMap<String, Integer> ages = new HashMap<>();
        ages.put("Alice", 30);
        ages.put("Bob", 25);

        // Lookup — expected O(1)
        int age = ages.get("Bob");

        System.out.println(STR."Bob's age: \{age}");
    }

    /*
     O(n) or linear time

     An algorithm is O(n) if the time it takes grows linearly with the size of the input (n)
     */
    private void linearTime() {
        // Example 1: Loop through an Array
        int[] numbers = {10, 20, 30, 40, 50};
        // O(n) loop — each element is visited once
        for (int i = 0; i < numbers.length; i++) {
            System.out.println(STR."Number: \{numbers[i]}");
        }

        // Example 2: Finding the Max Value
        int[] scores = {10, 60, 30, 80, 50};
        int max = scores[0];
        // O(n) — must compare every value
        for (int i = 1; i < scores.length; i++) {
            if (scores[i] > max) {
                max = scores[i];
            }
        }
        System.out.println(STR."Max score: \{max}");
    }

    /*
     O(n²) or quadratic time

     An algorithm is O(n²) if the time it takes grows proportional to the square of the input size.
     If the input size doubles, the time grows 4× (2²).
     If the input triples, the time grows 9× (3²).
     Quadratic time often comes from nested loops, where for every element you do work involving every other element.
     */
    private void quadraticTime() {
        // Example 1: Comparing Every Pair
        int[] nums = {1, 2, 3};
        // Nested loop — O(n²)
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums.length; j++) {
                System.out.println(STR."(\{nums[i]}, \{nums[j]})");
            }
        }

        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        for (int[] matrixRow : matrix) {
            for (int i = 0; i < matrixRow.length; i++) {
                System.out.print(matrixRow[i]);
            }
            System.out.println();
        }

        int[] nums2 = {1, 2, 3};
        for (int i = 0; i < nums2.length; i++) {
            for (int j = 0; j < nums2.length; j++) {
                for (int k = 0; k < nums2.length; k++) {
                    System.out.println(STR."(outer \{i}, middle \{j}, inner \{k})");
                }
            }
        }
    }

    /**
     O(log n) or logarithmic time

     An algorithm is O(log n) if it cuts the input size down by half (or some constant fraction) at each step.
     If the input doubles, the number of steps increases only slightly.
     This is much faster than O(n) or O(n²) for large inputs.

     Imagine guessing a number between 1 and 100:
     If you guess 50, then 25, then 12, etc., you're halving the range each time.
     That’s a binary search — classic O(log n).
     */
    private void logarithmicTime() {
        boolean found = binarySearch(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, 2);
        System.out.println(STR."Found: \{found}");
    }

    public boolean binarySearch(int[] sortedArr, int target) {
        int left = 0;
        int right = sortedArr.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (sortedArr[mid] == target) {
                return true;
            } else if (sortedArr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return false;
    }

    /**
     O(n log n)

     O(n log n) occurs when:
     You process all n elements, but for each element (or in total), you also do log n work.
     It’s faster than O(n²), but slower than O(n).
     */
    private void ohNlogN() {
        mergeSort(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
    }

    public void mergeSort(int[] arr) {
        if (arr.length < 2) return;

        int mid = arr.length / 2;
        int[] left = Arrays.copyOfRange(arr, 0, mid);
        int[] right = Arrays.copyOfRange(arr, mid, arr.length);

        mergeSort(left);
        mergeSort(right);
        merge(arr, left, right);
    }

    private void merge(int[] arr, int[] left, int[] right) {
        int i = 0, j = 0, k = 0;

        while (i < left.length && j < right.length) {
            arr[k++] = (left[i] < right[j]) ? left[i++] : right[j++];
        }

        while (i < left.length) arr[k++] = left[i++];
        while (j < right.length) arr[k++] = right[j++];
    }
}
