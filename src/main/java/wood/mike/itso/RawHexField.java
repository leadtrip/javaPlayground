package wood.mike.itso;

public class RawHexField implements ItsoElement{

    private final String hex;

    public RawHexField(String hex) {
        this.hex = hex;
    }

    @Override
    public String toTransportFormat() {
        return hex;
    }
}
