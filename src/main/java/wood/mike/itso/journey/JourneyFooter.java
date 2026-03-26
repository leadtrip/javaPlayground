package wood.mike.itso.journey;

import wood.mike.itso.HexField;
import wood.mike.itso.ItsoElement;
import wood.mike.itso.RawHexField;

import java.util.List;

public class JourneyFooter implements MessageData{

    private long ipeIterationNumber;
    private long ipeIsamSequenceNumber;
    private String itsoShellReferenceNumberEncrypted;

    @Override
    public List<ItsoElement> getFields() {
        return List.of(
                new HexField(ipeIterationNumber, 4),
                new HexField(ipeIsamSequenceNumber, 3),
                new RawHexField(itsoShellReferenceNumberEncrypted)
        );
    }
}
