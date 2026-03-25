package wood.mike.bitmanipulation.buspass;

public class BusPassTuned {
    private static final int PEAK_TIME_ALLOWED = 1;
    private static final int PASSENGER_ALLOWED = 1 << 1;
    private static final int DOG_ALLOWED = 1 << 2;
    private static final int IS_CONCESSIONARY = 1 << 3;
    private static final int REQUIRES_ASSISTANCE = 1 << 4;
    private static final int IS_JUNIOR = 1 << 5;
    private static final int IS_OAP = 1 << 6;
    private static final int WEEKEND_TRAVEL_ALLOWED = 1 << 7;

    private final int pass;

    public BusPassTuned(int pass) {
        this.pass = pass;
    }

    public boolean isPeakTimeAllowed() {
        return (pass & PEAK_TIME_ALLOWED) !=0;
    }

    public boolean isPassengerAllowed() {
        return (pass & PASSENGER_ALLOWED) !=0;
    }

    public boolean isDogAllowed() {
        return (pass & DOG_ALLOWED) !=0;
    }

    public boolean isConcessionary() {
        return (pass & IS_CONCESSIONARY) !=0;
    }

    public boolean requiresAssistance() {
        return (pass & REQUIRES_ASSISTANCE) !=0;
    }

    public boolean isJunior() {
        return (pass & IS_JUNIOR) !=0;
    }

    public boolean isOap() {
        return (pass & IS_OAP) !=0;
    }

    public boolean weekendTravelAllowed() {
        return (pass & WEEKEND_TRAVEL_ALLOWED) !=0;
    }
}
