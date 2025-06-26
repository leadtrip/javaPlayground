package wood.mike.sorting;

import java.util.Arrays;

public class SortingPlay {

    static final int[] numberArr = {29, 10, 2195, 1, 40, 2, 30, 54, 3};

    public static void main(String[] args) {
        SortingPlay play = new SortingPlay();
        play.selectionSort(numberArr.clone());
    }

    private void selectionSort(int[] arr) {
        int arrLen = arr.length;
        for (int i = 0; i < arrLen - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < arrLen; j++) {
                if (arr[j] < arr[minIdx]) {
                    minIdx = j;
                }
            }
            int temp = arr[i];
            arr[i] = arr[minIdx];
            arr[minIdx] = temp;
        }
        System.out.println(STR."Sorted array is \{Arrays.toString(arr)}");
    }
}
