package wood.mike.bitmanipulation;

import org.junit.jupiter.api.Test;
import wood.mike.bitmanipulation.buspass.BusPass;

import static org.junit.jupiter.api.Assertions.*;

public class BusPassTest {

    @Test
    public void testAll() {
        int pass = 0b01110101100;
        BusPass busPass = new BusPass(pass);
        assertFalse(busPass.isPeakTimeAllowed());
        assertFalse(busPass.isPassengerAllowed());
        assertTrue(busPass.isDogAllowed());
        assertTrue(busPass.isConcessionary());
        assertFalse(busPass.requiresAssistance());
        assertTrue(busPass.isJunior());
        assertFalse(busPass.isOap());
        assertTrue(busPass.weekendTravelAllowed());
        assertEquals(3, busPass.zoneId());
    }
}
