package wood.mike.itso.journey;

import wood.mike.itso.StandardElements;

public class JourneyRecordRfr2 extends AbstractJourneyRecord{

    public JourneyRecordRfr2(
            StandardElements standard,
            JourneyCore core,
            JourneyFooter footer
    ) {
        fields.add(standard);
        fields.addAll(core.getFields());
        fields.addAll(footer.getFields());
    }
}
