package wood.mike.itso;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

public class TransactionRecordTest {

    @Test
    public void testToTransportFormat() {
        TransactionRecord record = new TransactionRecord();
        record.setStandarElements(getStandardElements());

        System.out.println(record.toTransportFormat());
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
