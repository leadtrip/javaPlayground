package wood.mike.itso.journey;

import wood.mike.itso.HexField;
import wood.mike.itso.ItsoElement;
import wood.mike.itso.OID;
import wood.mike.itso.RawHexField;

import java.util.List;

public class JourneyCore implements MessageData{

    private int amountPaid;
    private int normalPrice;
    private int currencyCode;
    private String location;
    private String destination;
    private int concessionaryAuthority;
    private int productRetailer;
    private int transactionSequenceNumber;
    private int remainingUses;
    private int cpicc;
    private int transactionType;

    public JourneyCore setAmountPaid(int amountPaid) {
        this.amountPaid = amountPaid;
        return this;
    }

    public JourneyCore setNormalPrice(int normalPrice) {
        this.normalPrice = normalPrice;
        return this;
    }

    public JourneyCore setCurrencyCode(int currencyCode) {
        this.currencyCode = currencyCode;
        return this;
    }

    public JourneyCore setLocation(String location) {
        this.location = location;
        return this;
    }

    public JourneyCore setDestination(String destination) {
        this.destination = destination;
        return this;
    }

    public JourneyCore setConcessionaryAuthority(int concessionaryAuthority) {
        this.concessionaryAuthority = concessionaryAuthority;
        return this;
    }

    public JourneyCore setProductRetailer(int productRetailer) {
        this.productRetailer = productRetailer;
        return this;
    }

    public JourneyCore setTransactionSequenceNumber(int transactionSequenceNumber) {
        this.transactionSequenceNumber = transactionSequenceNumber;
        return this;
    }

    public JourneyCore setRemainingUses(int remainingUses) {
        this.remainingUses = remainingUses;
        return this;
    }

    public JourneyCore setCpicc(int cpicc) {
        this.cpicc = cpicc;
        return this;
    }

    public JourneyCore setTransactionType(int transactionType) {
        this.transactionType = transactionType;
        return this;
    }

    @Override
    public List<ItsoElement> getFields() {
        return List.of(
                new HexField(amountPaid, 4),
                new HexField(normalPrice, 4),
                new HexField(currencyCode, 1),
                new RawHexField(location),
                new RawHexField(destination),
                new HexField(concessionaryAuthority, 2),
                new OID(productRetailer),
                new HexField(transactionSequenceNumber, 2),
                new HexField(remainingUses, 1),
                new HexField(cpicc, 2),
                new HexField(transactionType, 1)
        );
    }
}
