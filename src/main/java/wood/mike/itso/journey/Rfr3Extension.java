package wood.mike.itso.journey;

import wood.mike.itso.HexField;
import wood.mike.itso.ItsoElement;
import wood.mike.itso.RawHexField;

import java.util.List;

public class Rfr3Extension implements MessageData{

    private String serviceOperatorId;
    private String serviceNumber;
    private String tripOrTrainNumber;
    private int reimbursementDataFlags;
    private String supplementaryData;

    public Rfr3Extension setServiceOperatorId(String serviceOperatorId) {
        this.serviceOperatorId = serviceOperatorId;
        return this;
    }

    public Rfr3Extension setServiceNumber(String serviceNumber) {
        this.serviceNumber = serviceNumber;
        return this;
    }

    public Rfr3Extension setTripOrTrainNumber(String tripOrTrainNumber) {
        this.tripOrTrainNumber = tripOrTrainNumber;
        return this;
    }

    public Rfr3Extension setReimbursementDataFlags(int reimbursementDataFlags) {
        this.reimbursementDataFlags = reimbursementDataFlags;
        return this;
    }

    public Rfr3Extension setSupplementaryData(String supplementaryData) {
        this.supplementaryData = supplementaryData;
        return this;
    }

    @Override
    public List<ItsoElement> getFields() {
        return List.of(
                new RawHexField(serviceOperatorId),
                new RawHexField(serviceNumber),
                new RawHexField(tripOrTrainNumber),
                new HexField(reimbursementDataFlags, 1),
                new RawHexField(supplementaryData)
        );
    }
}
