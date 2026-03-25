package wood.mike.bitmanipulation.buspass;

import static wood.mike.bitmanipulation.BitUtils.*;

public class BusPass {
    private static final int PEAK_TIME_ALLOWED = 0;
    private static final int PASSENGER_ALLOWED = 1;
    private static final int DOG_ALLOWED = 2;
    private static final int IS_CONCESSIONARY = 3;
    private static final int REQUIRES_ASSISTANCE = 4;
    private static final int IS_JUNIOR = 5;
    private static final int IS_OAP = 6;
    private static final int WEEKEND_TRAVEL_ALLOWED = 7;

    // the zone ID is a 3 bit number, zero to seven, starting at bit 8 to 10
    private static final int ZONE_SHIFT = 8;          // Starts at bit 8
    private static final int ZONE_MASK  = 0b111 << ZONE_SHIFT; // Bits 8, 9, 10

    private final int pass;

    public BusPass(int pass) {
        this.pass = pass;
    }

    public boolean isPeakTimeAllowed() {
        return isBitSet(pass, PEAK_TIME_ALLOWED);
    }

    public boolean isPassengerAllowed() {
        return isBitSet(pass, PASSENGER_ALLOWED);
    }

    public boolean isDogAllowed() {
        return isBitSet(pass, DOG_ALLOWED);
    }

    public boolean isConcessionary() {
        return isBitSet(pass, IS_CONCESSIONARY);
    }

    public boolean requiresAssistance() {
        return isBitSet(pass, REQUIRES_ASSISTANCE);
    }

    public boolean isJunior() {
        return isBitSet(pass, IS_JUNIOR);
    }

    public boolean isOap() {
        return isBitSet(pass, IS_OAP);
    }

    public boolean weekendTravelAllowed() {
        return isBitSet(pass, WEEKEND_TRAVEL_ALLOWED);
    }

    public int zoneId() {
        return (pass & ZONE_MASK) >>> ZONE_SHIFT;
    }
}
