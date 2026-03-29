package wood.mike.itso;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserDefinedFieldTest {

    @Test
    public void testToAndFromTransportFormat() {
        String udValue = "X-29";
        UserDefinedField udf = new UserDefinedField(udValue);
        String hexFormat = udf.toTransportFormat();
        assertEquals("582d3239", hexFormat);
        assertEquals(udValue, udf.fromTransportFormat(hexFormat));

        int flags = 0b00000111;
        String flagsAsUd = String.valueOf(flags);
        UserDefinedField udfFlags = new UserDefinedField(flagsAsUd);
        String hexFlags = udfFlags.toTransportFormat();
        assertEquals(flagsAsUd, udf.fromTransportFormat(hexFlags));
    }
}
