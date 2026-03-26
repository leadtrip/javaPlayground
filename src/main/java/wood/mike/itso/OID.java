package wood.mike.itso;

public class OID implements ItsoElement {
    private final long value;

    public OID(long value) {
        if (value > 0x1FFF) {
            throw new IllegalArgumentException("Standard OID cannot exceed 13 bits (8191)");
        }
        this.value = value;
    }

    @Override
    public String toTransportFormat() {
        return new HexField(value, 4).toTransportFormat();
    }
}
