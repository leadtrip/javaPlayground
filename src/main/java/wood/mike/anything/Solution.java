package wood.mike.anything;

import java.util.Arrays;
import java.util.Scanner;

public class Solution {

    public static int[] funcBouquet(int flowerStick[], int random){
        int[] answer = new int[flowerStick.length];

        int[] firstN = Arrays.copyOfRange(flowerStick, 0, random);
        Arrays.sort(firstN);

        System.arraycopy(firstN, 0, answer, 0, firstN.length);

        int[] secondN = Arrays.copyOfRange(flowerStick, random, flowerStick.length);
        Arrays.sort(secondN);
        for (int i = 0; i < secondN.length; i++) {
            answer[i+random] = secondN[secondN.length - 1 - i];
        }

        return answer;
    }

    /**
     * inputs on command line:
     * 8
     * 11 7 5 10 46 23 16 8
     * 3
     * OR
     * 10
     * 1 2 3 4 5 6 7 8 9 10
     * 5
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int flowerStick_size = in.nextInt();
        int[] flowerStick = new int[flowerStick_size];
        for (int i = 0; i < flowerStick_size; i++) {
            flowerStick[i] = in.nextInt();
        }
        int random = in.nextInt();

        int[] result = funcBouquet(flowerStick, random);
        for (int i = 0; i < result.length; i++) {
            System.out.print(result[i] + " ");
        }
        System.out.println();
        System.out.println(result[result.length - 1]);
    }
}
