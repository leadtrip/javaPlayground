package wood.mike.itso;

import java.util.regex.Pattern;

public class IIN implements ItsoElement{

    private static final int EXPECTED_LENGTH = 6;
    private static final Pattern DIGITS_ONLY = Pattern.compile("\\d+");

    private final String iin;

    public IIN(String iin) {
        // Must be numeric and correct length
        if (iin == null || iin.length() != EXPECTED_LENGTH || !DIGITS_ONLY.matcher(iin).matches()) {
            throw new IllegalArgumentException("IIN must be exactly " + EXPECTED_LENGTH + " digits");
        }
        this.iin = iin;
    }

    /**
     * Factory for the standard ITSO IIN
     */
    public static IIN standard() {
        return new IIN("633597");
    }

    @Override
    public String toTransportFormat() {
        return iin;
    }

    public String getValue() {
        return iin;
    }
}
