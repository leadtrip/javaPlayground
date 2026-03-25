package wood.mike.bitmanipulation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static wood.mike.bitmanipulation.BitUtils.*;

public class BitUtilsTest {

    @Test
    public void testAll() {
        int num = 0b0101;
        int modBit = 0;
        assertTrue(isBitSet(num, modBit));
        int cleared = clearBit(num, modBit);
        assertEquals(num-1, cleared);
        assertFalse(isBitSet(clearBit(num, modBit), modBit));
        assertEquals(num, setBit(num, modBit));
        assertTrue(isBitSet(num, modBit));
    }
}
