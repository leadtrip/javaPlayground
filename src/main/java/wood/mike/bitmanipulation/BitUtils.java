package wood.mike.bitmanipulation;

public final class BitUtils {
    private BitUtils() {

    }

    /**
     * Create a mask by moving 1 to the left the desired number of times
     * e.g. say we want to check bit 4 in the number 18 (0b00010010)
     * create the mask = (1 << 4) = 0b00001000
     * Perform OR on mask and number:
     * 0b00001000 |
     * 0b00010010
     * ----------
     * 0b00011010
     * ----------
     * the rule for OR is that the result is 1 if either bit is 1
     */
    public static int setBit(int num, int bit) {
        return (num | (1 << bit));
    }


    /**
     * Create a mask by moving 1 to the left the desired number of times
     * e.g. say we want to check bit 4 in the number 18 (0b00010010) then negate/invert it with ~
     * create the mask = ~(1 << 2) = 0b11111101
     * Perform AND on mask and number
     * 0b11111101
     * 0b00010010
     * ----------
     * 0b00010000
     * ----------
     * the rule for AND is that result is 1 if both bits are 1 else zero
     */
    public static int clearBit(int num, int bit) {
        return (num & ~(1 << bit));
    }

    /**
     * Create a mask by moving 1 to the left the desired number of times
     * e.g. say we want to check bit 4 in the number 18 (0b00010010)
     * create the mask = (1 << 4) = 0b00001000
     * Perform AND on mask and number
     * 0b00010010
     * 0b00001000
     * ----------
     * 0b00000000
     * ----------
     * if result is zero bit isn't set
     */
    public static boolean isBitSet(int num, int bit) {
        return ((num & (1 << bit)) != 0);
    }
}
