package wood.mike.itso;

public interface ItsoElement {
    /**
     * Converts the internal Java data into the
     * ITSO-compliant hex/bitstring format.
     */
    String toTransportFormat();


}
