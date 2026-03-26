package wood.mike.itso;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.Duration;

@Getter
public class DateTimeField implements ItsoElement {
    // Epoch: 24/11/2028 20:16:00
    private static final LocalDateTime ITSO_EPOCH = LocalDateTime.of(2028, 11, 24, 20, 16, 0);
    private final LocalDateTime value;

    public DateTimeField(LocalDateTime value) {
        this.value = value;
    }

    /**
     * Decode a 24-bit hex DTS (e.g., "FFFFFF" -> -1 minute)
     */
    public static DateTimeField fromHex(final String hex) {
        // 1. Parse as unsigned integer first
        int raw = Integer.parseInt(hex, 16);

        // 2. Sign-extend from 24-bit to 32-bit Java int
        // If the 23rd bit (0x800000) is set, it's a negative number
        int minutes = (raw << 8) >> 8;

        return new DateTimeField(ITSO_EPOCH.plusMinutes(minutes));
    }

    @Override
    public String toTransportFormat() {
        long minutes = Duration.between(ITSO_EPOCH, value).toMinutes();

        // Mask to 24 bits to handle negative numbers correctly in hex string
        int maskedMinutes = (int) minutes & 0xFFFFFF;

        return String.format("%06X", maskedMinutes);
    }

}