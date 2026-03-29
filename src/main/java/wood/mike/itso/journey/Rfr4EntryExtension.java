package wood.mike.itso.journey;

import wood.mike.itso.*;

import java.time.LocalDateTime;
import java.util.List;

public class Rfr4EntryExtension implements MessageData{

    private String entryTtIpeIsamId;
    private int entryTtIpeIsamSequenceNumber;
    private LocalDateTime entryDateTimeStamp;
    private OID entryOid;
    private int entryIinIndex;

    public Rfr4EntryExtension setEntryTtIpeIsamId(String entryTtIpeIsamId) {
        this.entryTtIpeIsamId = entryTtIpeIsamId;
        return this;
    }

    public Rfr4EntryExtension setEntryTtIpeIsamSequenceNumber(int entryTtIpeIsamSequenceNumber) {
        this.entryTtIpeIsamSequenceNumber = entryTtIpeIsamSequenceNumber;
        return this;
    }

    public Rfr4EntryExtension setEntryDateTimeStamp(LocalDateTime entryDateTimeStamp) {
        this.entryDateTimeStamp = entryDateTimeStamp;
        return this;
    }

    public Rfr4EntryExtension setEntryOid(OID entryOid) {
        this.entryOid = entryOid;
        return this;
    }

    public Rfr4EntryExtension setEntryIinIndex(int entryIinIndex) {
        this.entryIinIndex = entryIinIndex;
        return this;
    }

    @Override
    public List<ItsoElement> getFields() {
        return List.of(
                new RawHexField(entryTtIpeIsamId),
                new HexField(entryTtIpeIsamSequenceNumber, 6),
                new DateTimeField(entryDateTimeStamp),
                entryOid,
                new HexField(entryIinIndex, 1)
        );
    }
}
