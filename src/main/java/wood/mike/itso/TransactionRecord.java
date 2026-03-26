package wood.mike.itso;

public class TransactionRecord implements ItsoElement{

    private StandardElements standardElements;

    public TransactionRecord setStandarElements(final StandardElements standardElements) {
        this.standardElements = standardElements;
        return this;
    }

    @Override
    public String toTransportFormat() {
        return standardElements.toTransportFormat();
    }

}
