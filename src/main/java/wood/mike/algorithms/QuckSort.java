package wood.mike.algorithms;

import java.util.Random;

/**
 * 3 steps
 * 1. choose pivot
 *      + a random number in the collection
 * 2. partition
 *      + move all numbers < pivot to left & all numbers > pivot to right
 * 3. sort the left and right subarrays created by partitioning
 *
 */
public class QuckSort {

    public static void main(String[] args) {
        Random rand = new Random();
        int[] numbers = new int[10];

        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = rand.nextInt(100);
        }

        System.out.println("Before:");
        printArray(numbers);

        quicksort(numbers);

        System.out.println("\nAfter:");
        printArray(numbers);
    }

    private static void quicksort(int[] array) {
        quicksort(array, 0, array.length - 1);
    }

    private static void quicksort(int[] array, int lowIndex, int highIndex) {

        if (lowIndex >= highIndex) {
            return;
        }

        // 1. Find a “pivot” item in the array. This item is the basis for comparison for a single round
        int pivotIndex = new Random().nextInt(highIndex - lowIndex) + lowIndex;
        int pivot = array[pivotIndex];
        swap(array, pivotIndex, highIndex);

        int leftPointer = partition(array, lowIndex, highIndex, pivot);

        quicksort(array, lowIndex, leftPointer - 1);
        quicksort(array, leftPointer + 1, highIndex);

    }

    private static int partition(int[] array, int lowIndex, int highIndex, int pivot) {
        /*
         * 2. Start a pointer (the left pointer) at the first item in the array.
         * 3. Start a pointer (the right pointer) at the last item in the array.
         */
        int leftPointer = lowIndex;
        int rightPointer = highIndex - 1;

        while (leftPointer < rightPointer) {

            // While the value at the left pointer in the array is less than the pivot value, move the left pointer to the right (add 1).
            // Continue until the value at the left pointer is greater than or equal to the pivot value.
            while (array[leftPointer] <= pivot && leftPointer < rightPointer) {
                leftPointer++;
            }

            // While the value at the right pointer in the array is greater than the pivot value, move the right pointer to the left (subtract 1).
            // Continue until the value at the right pointer is less than or equal to the pivot value.
            while (array[rightPointer] >= pivot && leftPointer < rightPointer) {
                rightPointer--;
            }

            swap(array, leftPointer, rightPointer);
        }
        swap(array, leftPointer, highIndex);
        return leftPointer;
    }

    private static void swap(int[] array, int index1, int index2) {
        int temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }

    private static void printArray(int[] numbers) {
        for (int i = 0; i < numbers.length; i++) {
            System.out.println(numbers[i]);
        }
    }
}
