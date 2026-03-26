package wood.mike.itso;

public interface ItsoElement {
    /**
     * Converts the internal Java data into the
     * ITSO-compliant hex/bitstring format.
     */
    String toTransportFormat();

    /**
     * Optional: Returns the raw bit-length if the spec
     * requires padding or specific alignment.
     */
    default int bitLength() {
        return 0;
    }
}
