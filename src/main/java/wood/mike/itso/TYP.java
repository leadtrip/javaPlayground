package wood.mike.itso;

public class TYP implements ItsoElement {
    private final long value;

    public TYP(long value) {
        if (value < 0x01 || value > 0x1F) {
            throw new IllegalArgumentException("TYP must be between 0x01 and 0x1F");
        }
        this.value = value;
    }

    @Override
    public String toTransportFormat() {
        return new HexField(value, 2).toTransportFormat();
    }
}
