package wood.mike.itso.journey;

import wood.mike.itso.StandardElements;

public class JourneyRecordRfr3 extends AbstractJourneyRecord{

    public JourneyRecordRfr3(
            StandardElements standard,
            JourneyCore core,
            Rfr3Extension rfr3Extension,
            JourneyFooter footer
    ) {
        fields.add(standard);
        fields.addAll(core.getFields());
        fields.addAll(rfr3Extension.getFields());
        fields.addAll(footer.getFields());
    }
}
