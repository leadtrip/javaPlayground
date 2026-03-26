package wood.mike.itso;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;


public class StandardElementsTest {

    @Test
    public void testToTransportFormat() {
        StandardElements standardElements = new StandardElements();
        standardElements
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
        String transportFormat = standardElements.toTransportFormat();
        Assertions.assertEquals("04,A31E97,00,00000000,00,07,04,01,6335970080160E,00", transportFormat);
    }
}
