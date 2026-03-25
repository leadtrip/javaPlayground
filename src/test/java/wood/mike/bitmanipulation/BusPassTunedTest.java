package wood.mike.bitmanipulation;

import org.junit.jupiter.api.Test;
import wood.mike.bitmanipulation.buspass.BusPassTuned;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BusPassTunedTest {

    @Test
    public void testAll() {
        int pass = 0b10101100;
        BusPassTuned busPass = new BusPassTuned(pass);
        assertFalse(busPass.isPeakTimeAllowed());
        assertFalse(busPass.isPassengerAllowed());
        assertTrue(busPass.isDogAllowed());
        assertTrue(busPass.isConcessionary());
        assertFalse(busPass.requiresAssistance());
        assertTrue(busPass.isJunior());
        assertFalse(busPass.isOap());
        assertTrue(busPass.weekendTravelAllowed());
    }
}
