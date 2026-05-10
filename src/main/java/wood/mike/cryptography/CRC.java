package wood.mike.cryptography;

import java.nio.charset.StandardCharsets;
import java.util.zip.CRC32;

public class CRC {

    static void main() {
        new CRC().run();
    }

    private void run() {
        String itsoData = "04,A31E97,00,00000000,00,07,04,01,6335970080160E,00";
        long crc32 = calculateCRC32(itsoData.getBytes(StandardCharsets.US_ASCII));
        System.out.println(Long.toHexString(crc32));

        long crc16 = calculateCRC16(itsoData.getBytes(StandardCharsets.US_ASCII));
        System.out.println(Long.toHexString(crc16));
    }

    public long calculateCRC32(byte[] data) {
        CRC32 crc = new CRC32();
        crc.update(data);
        return crc.getValue();
    }

    public int calculateCRC16(byte[] bytes) {
        int crc = 0xFFFF;
        int polynomial = 0x1021;

        for (byte b : bytes) {
            for (int i = 0; i < 8; i++) {
                boolean bit = ((b >> (7 - i) & 1) == 1);
                boolean c15 = ((crc >> 15 & 1) == 1);
                crc <<= 1;
                if (c15 ^ bit) crc ^= polynomial;
            }
        }
        return crc & 0xFFFF;
    }
}
