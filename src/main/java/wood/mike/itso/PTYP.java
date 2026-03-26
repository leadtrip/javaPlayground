package wood.mike.itso;

public class PTYP implements ItsoElement{

    private Long value;

    public PTYP(final long value) {
        if (value < 0x01 || value > 0x1F) {
            throw new IllegalArgumentException("PTYP must be between 0x01 and 0x1F");
        }
        this.value = value;
    }

    @Override
    public String toTransportFormat() {
        return new HexField(value, 2).toTransportFormat();
    }
}
