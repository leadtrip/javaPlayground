package wood.mike.anything;

import java.util.Scanner;
import java.util.Arrays;

public class ReallyAnything {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String strSent = scanner.nextLine();

        String strReceived = scanner.nextLine();

        char result = lostCharNW(strSent, strReceived);
        System.out.println(result);
    }

    public static char lostCharNW(String strSent, String strReceived) {
        String longer, shorter;

        if (strSent.length() > strReceived.length()) {
            longer = strSent;
            shorter = strReceived;
        } else {
            longer = strReceived;
            shorter = strSent;
        }

        char[] longerArray = longer.toCharArray();
        Arrays.sort(longerArray);
        char[] shorterArray = shorter.toCharArray();
        Arrays.sort(shorterArray);

        for (int i = 0; i < shorterArray.length; i++) {
            if (longerArray[i] != shorterArray[i]) {
                return longerArray[i];
            }
        }

        return longerArray[longerArray.length - 1];
    }
}