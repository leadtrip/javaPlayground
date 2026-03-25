package wood.mike.bitmanipulation.buspass;

import java.util.EnumSet;

public class BusPassMega {

    private final EnumSet<PassFeature> pass;

    public BusPassMega(int pass) {
        EnumSet<PassFeature> set = EnumSet.noneOf(PassFeature.class);
        for (PassFeature feature : PassFeature.values()) {
            if ((pass & (1 << feature.ordinal())) != 0) {
                set.add(feature);
            }
        }
        this.pass = set;
    }

    public int encode() {
        int bitmask = 0;
        for (PassFeature feature : pass) {
            bitmask |= (1 << feature.ordinal());
        }
        return bitmask;
    }


    public boolean isPeakTimeAllowed() {
        return pass.contains(PassFeature.PEAK_TIME_ALLOWED);
    }

    public boolean isPassengerAllowed() {
        return pass.contains(PassFeature.PASSENGER_ALLOWED);
    }

    public boolean isDogAllowed() {
        return pass.contains(PassFeature.DOG_ALLOWED);
    }

    public boolean isConcessionary() {
        return pass.contains(PassFeature.IS_CONCESSIONARY);
    }

    public boolean requiresAssistance() {
        return pass.contains(PassFeature.REQUIRES_ASSISTANCE);
    }

    public boolean isJunior() {
        return pass.contains(PassFeature.IS_JUNIOR);
    }

    public boolean isOap() {
        return pass.contains(PassFeature.IS_OAP);
    }

    public boolean weekendTravelAllowed() {
        return pass.contains(PassFeature.WEEKEND_TRAVEL_ALLOWED);
    }
}
