package wood.mike.itso;

public class IsamId implements ItsoElement{

    private final String isamId;

    public IsamId(final String isamId) {
        this.isamId = isamId;
    }

    @Override
    public String toTransportFormat() {
        return "";
    }

}
