package wood.mike.bitmanipulation;

import lombok.extern.slf4j.Slf4j;

import java.util.function.BiFunction;

import static wood.mike.bitmanipulation.BitUtils.*;

@Slf4j
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
        clearBits();
    }

    void printBinaryHex() {
        log.info("1 to 20 decimal, binary, hex");
        for(int i = 1; i <= 20; i++) {
            log.info(valueBinaryHex(i));
        }
        log.info("Doubling decimal, binary, hex");
        int tally = 2;
        for(int i = 0; i < 10; i++) {
            log.info(valueBinaryHex(tally));
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
        log.info("NOT - Inverts all the bits in the given value");
        int a = 5;
        int b = ~a;
        log.info("{}",binForm(a, 32));
        log.info("{}",binForm(b));
    }

    void leftShift() {
        for (int i = 1;i <= 3; i++){
            int a = 0x00000001;
            log.info("**** Left shift by {} ****", i);
            log.info(valueBinaryHex(a));
            for(int j=1;j<5;j++) {
                int res = a << i;
                a = res;
                log.info(valueBinaryHex(res));
            }
        }
    }

    void rightShift() {
        for (int i = 1;i <= 3; i++){
            int a = 0x1000;
            log.info("**** Right shift by {} ****", i);
            log.info(valueBinaryHex(a));
            for(int j=1;j<5;j++) {
                int res = a >> i;
                a = res;
                log.info(valueBinaryHex(res));
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
            log.info("Bit {} is set? {}", i, isBitSet(num, i));
        }

        // this loop breaks things down to see each step's inputs
        for(int i=0;i<8;i++) {
            // The bitMask is created by shifting the number 1 to the left by the number of positions we want to set.
            int mask = 1 << i;
            log.info("num {}", binForm(num, 8));
            log.info("msk {}", binForm(mask, 8));
            int res = num & mask;
            log.info("Bit {} is set? {}", i, (res!=0) ? "yes": "no");
        }
    }

    void setBits() {
        log.info("Set a bit");
        int num = 0b00000000;
        log.info("num {}", binForm(num, 8));
        for (int i=0;i<8;i++) {
            int i1 = setBit(num, i);
            num = i1;
            log.info("num {}", binForm(i1, 8));
        }

        log.info("**** Manual set a bit ****");
        // this loop breaks things down to see each step's inputs
        num = 0b00000000;
        for(int i=0;i<8;i++) {
            int mask = 1 << i;      // create mask by shifting i number of places starting at one
            int res = num | mask;   // the rule for OR is that the result is 1 if either bit is 1
            num = res;
            log.info("num {}", binForm(num, 8));
            log.info("msk {}", binForm(mask, 8));
            log.info("Bit {} is set? {}}", i, (res!=0) ? "yes": "no");
        }
    }

    void clearBits() {
        log.info("Clear a bit");
        int num = 0b11111111;
        log.info("num {}", binForm(num, 8));
        for (int i=0;i<8;i++) {
            int i1 = clearBit(num, i);
            num = i1;
            log.info("num {}", binForm(i1, 8));
        }
    }

    private static String bin(int num) {
        return Integer.toBinaryString(num);
    }

    private static String binForm(int num) {
        return String.format("%s",  String.format("%8s",bin(num)).replaceAll(" ", "0"));
    }

    private static String binForm(int num, int times) {
        String binaryRep = bin(num);
        return rep(times - binaryRep.length(), "0") + bin(num);
    }

    private static String valueBinaryHex(int i) {
        return String.format("%5d %14s 0x%04X", i, binForm(i, 14), i);
    }

    private static void calc(BiFunction<Integer, Integer, Integer> func, String desc) {
        log.info(desc);
        Integer res = func.apply(A, B);
        log.info(binForm(A));
        log.info(binForm(B));
        eq(res);
    }

    private static void eq(int val) {
        log.info(rep(8, "-"));
        log.info(binForm(val));
        log.info(rep(8, "-"));
    }

    private static String rep(int times, String st) {
        return String.valueOf(st).repeat(Math.max(0, times));
    }
}
