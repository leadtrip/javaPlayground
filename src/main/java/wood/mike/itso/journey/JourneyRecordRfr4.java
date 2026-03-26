package wood.mike.itso.journey;

import wood.mike.itso.StandardElements;

public class JourneyRecordRfr4 extends AbstractJourneyRecord{

    public JourneyRecordRfr4(
            StandardElements standard,
            JourneyCore core,
            Rfr3Extension rfr3,
            Rfr4EntryExtension entry,
            JourneyFooter footer
    ) {
        fields.add(standard);
        fields.addAll(core.getFields());
        fields.addAll(rfr3.getFields());   // Added in RFR 3
        fields.addAll(entry.getFields());  // Inserted in RFR 4
        fields.addAll(footer.getFields()); // The "Common Footer"
    }
}
