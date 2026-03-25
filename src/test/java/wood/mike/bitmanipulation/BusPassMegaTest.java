package wood.mike.bitmanipulation;

import org.junit.jupiter.api.Test;
import wood.mike.bitmanipulation.buspass.BusPassMega;

import static org.junit.jupiter.api.Assertions.*;

public class BusPassMegaTest {

    @Test
    public void testAll() {
        int pass = 0b10101100;
        BusPassMega busPass = new BusPassMega(pass);
        assertFalse(busPass.isPeakTimeAllowed());
        assertFalse(busPass.isPassengerAllowed());
        assertTrue(busPass.isDogAllowed());
        assertTrue(busPass.isConcessionary());
        assertFalse(busPass.requiresAssistance());
        assertTrue(busPass.isJunior());
        assertFalse(busPass.isOap());
        assertTrue(busPass.weekendTravelAllowed());
        assertEquals(pass, busPass.encode());
    }
}
