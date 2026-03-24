package wood.mike.bitmanipulation;

import java.math.BigInteger;
import java.text.Format;
import java.util.Formatter;
import java.util.function.BiFunction;
import java.util.function.Function;

public class BitManipulation {

    static final int A = 0xF;
    static final int B = 0x11;

    static void main() {
        new BitManipulation().run();
    }

    private void run() {
        printBinaryHex();
        operatorAND();
        operatorOR();
        operatorXOR();
        operatorNOT();
        leftShift();
        rightShift();
        checkBits();
        setBits();
    }

    void printBinaryHex() {
        System.out.println("1 to 30 decimal, binary, hex");
        for(int i = 1; i <= 30; i++) {
            System.out.printf("%d %s 0x%04X %n", i, bin(i), i);
        }
        System.out.println("Doubling decimal, binary, hex");
        int tally = 2;
        for(int i = 0; i < 30; i++) {
            System.out.printf("%d %s 0x%04X %n", tally, bin(tally), tally);
            tally *= 2;
        }
    }

    void operatorAND() {
        calc((x, y)-> x & y, "AND - If both bits are 1 then 1 else zero");
    }

    void operatorOR() {
        calc((x, y)-> x | y, "OR - If at least one bit is 1 then 1 else zero");
    }

    void operatorXOR() {
        calc((x, y)-> x ^ y, "XOR - If both bits are the same zero else 1");
    }

    void operatorNOT() {
        System.out.println("NOT - Inverts all the bits in the given value");
        int a = 5;
        int b = ~a;
        System.out.print(binForm(a));
        System.out.print(binForm(b));
    }

    void leftShift() {
        for (int i = 1;i <= 4; i++){
            int a = 0x00000001;
            System.out.printf("**** Left shift by %d ****%n", i);
            System.out.print(valueBinaryHex(a));
            for(int j=1;j<5;j++) {
                int res = a << i;
                a = res;
                System.out.print(valueBinaryHex(res));
            }
        }
    }

    void rightShift() {
        for (int i = 1;i <= 4; i++){
            int a = 0x1000;
            System.out.printf("**** Right shift by %d ****%n", i);
            System.out.print(valueBinaryHex(a));
            for(int j=1;j<5;j++) {
                int res = a >> i;
                a = res;
                System.out.print(valueBinaryHex(res));
            }
        }
    }

    /**
     * Create a mask where the only bit set is the one we want to check, we then perform
     * a logical AND operation on the number to check and the mask.
     * Example say we want to check if bit 2 is set in the number 212
     * mask = 00000100
     * num  = 11010100
     * AND returns 1 only if both bits are set which they are so we know bit 2 is set/on
     * All other values in the mask are zero so are guaranteed to have no influence in the calculation.
     */
    void checkBits() {
        int num = 0b11100001;
        // this first loop uses the isBitSet method
        for(int i=0;i<8;i++) {
            System.out.printf("Bit %d is set? %s%n", i, isBitSet(num, i));
        }

        // this loop breaks things down to see each step's inputs
        for(int i=0;i<8;i++) {
            // The bitMask is created by shifting the number 1 to the left by the number of positions we want to set.
            int mask = 1 << i;
            System.out.printf("numb %s%n", binForm(num, 8));
            System.out.printf("mask %s%n", binForm(mask, 8));
            int res = num & mask;
            System.out.printf("Bit %d is set? %s%n", i, (res!=0) ? "yes": "no");
        }
    }

    void setBits() {
        System.out.println("Set a bit");
        int num = 0b00000000;
        System.out.printf("numb %s%n", binForm(num, 8));
        for (int i=0;i<8;i++) {
            int i1 = setBit(num, i);
            num = i1;
            System.out.printf("numb %s%n", binForm(i1, 8));
        }

        System.out.println("**** Manual set a bit ****");
        // this loop breaks things down to see each step's inputs
        num = 0b00000000;
        for(int i=0;i<8;i++) {
            int mask = 1 << i;
            int res = num | mask;
            num = res;
            System.out.printf("numb %s%n", binForm(num, 8));
            System.out.printf("mask %s%n", binForm(mask, 8));
            System.out.printf("Bit %d is set? %s%n", i, (res!=0) ? "yes": "no");
        }
    }

    /**
     * Create a mask by moving 1 to the left the desired number of times e.g. say we want to check bit 4 in the number 18
     * or 0b00010010
     * create the mask = (1 << 4) == 0b00010000
     * Perform OR on mask and number:
     * 0b00010000 |
     * 0b00010010
     */
    int setBit(int num, int bit) {
        return (num | (1 << bit));
    }

    int clearBit(int num, int bit) {
        return num & ~(1 << bit);
    }

    boolean isBitSet(int num, int bit) {
        return ((num & (1 << bit)) != 0);
    }

    private static String bin(int num) {
        return Integer.toBinaryString(num);
    }

    private static String binForm(int num) {
        return String.format("%s%n",  String.format("%8s",bin(num)).replaceAll(" ", "0"));
    }

    private static String binForm(int num, int times) {
        String binaryRep = bin(num);
        return rep(times - binaryRep.length(), "0") + bin(num);
    }

    private static String valueBinaryHex(int i) {
        return String.format("%d %s 0x%04X %n", i, bin(i), i);
    }

    private static void calc(BiFunction<Integer, Integer, Integer> func, String desc) {
        System.out.println(desc);
        Integer res = func.apply(A, B);
        System.out.print(binForm(A));
        System.out.print(binForm(B));
        eq(res);
    }

    private static void eq(int val) {
        System.out.println(rep(8, "-"));
        System.out.print(binForm(val));
        System.out.println(rep(8, "-"));
    }

    private static String rep(int times, String st) {
        return String.valueOf(st).repeat(Math.max(0, times));
    }
}
