package wood.mike.itso;

public class HexField implements ItsoElement {

    private final long value;
    private final int hexChars;

    /**
     * @param value The numeric value to encode
     * @param hexChars The required length (e.g., 2 for a byte, 4 for a short)
     */
    public HexField(long value, int hexChars) {
        this.value = value;
        this.hexChars = hexChars;
    }

    @Override
    public String toTransportFormat() {
        // Hexadecimal, uppercase, padded with zeros
        return String.format(STR."%0\{hexChars}X", value);
    }
}
