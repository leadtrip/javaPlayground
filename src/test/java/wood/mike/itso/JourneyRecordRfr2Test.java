package wood.mike.itso;

import org.junit.jupiter.api.Test;
import wood.mike.itso.journey.JourneyCore;
import wood.mike.itso.journey.JourneyFooter;
import wood.mike.itso.journey.JourneyRecordRfr2;

import java.time.LocalDateTime;
import java.time.Month;

public class JourneyRecordRfr2Test {

    @Test
    public void testToTransportFormat() {
        JourneyRecordRfr2 jr = new JourneyRecordRfr2(
                getStandardElements(),
                getJourneyCore(),
                getJourneyFooter());
        System.out.println(jr.toTransportFormat());
    }

    private JourneyFooter getJourneyFooter() {
        return new JourneyFooter()
                .setIpeIsamSequenceNumber(1)
                .setIpeIterationNumber(1)
                .setItsoShellReferenceNumberEncrypted("00000063359701284224332300000000");
    }

    private JourneyCore getJourneyCore() {
        return new JourneyCore()
                .setAmountPaid(10)
                .setNormalPrice(10)
                .setCurrencyCode(0)
                .setLocation("")
                .setDestination("")
                .setConcessionaryAuthority(1)
                .setProductRetailer(1)
                .setTransactionSequenceNumber(1)
                .setRemainingUses(1)
                .setCpicc(1)
                .setTransactionType(1);
    }

    private StandardElements getStandardElements() {
        return new StandardElements()
                .setRecordFormatRevision(4)
                .setTransactionDateTime(LocalDateTime.of(2017, Month.APRIL, 29, 17, 59))
                .setTransactionInformation(0)
                .setStaffId(0)
                .setSupplementalInformation(0)
                .setFvc(7)
                .setKsc(4)
                .setKvc(1)
                .setIpeId("633597", 128, 22, 14)
                .setShellIterationNumber(0);
    }
}
