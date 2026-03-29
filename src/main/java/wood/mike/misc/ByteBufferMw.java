package wood.mike.misc;

import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.nio.ByteBuffer;

@Slf4j
public class ByteBufferMw {

    static void main() {
        new ByteBufferMw().run();
    }

    private void run() {
        ByteBuffer buffer = ByteBuffer.allocate(30);
        logPosition(buffer);    // 0

        // put a byte
        buffer.put((byte)8);
        logRange(buffer, 0,1);
        logPosition(buffer);    // 1, byte is 8 bits, 1 byte :)

        // put an int
        buffer.putInt(Integer.MAX_VALUE);
        logRange(buffer,1, 4);;
        logPosition(buffer);    // 5, Integer is 32 bits, 4 bytes

        // put a long
        buffer.putLong(Long.MAX_VALUE);
        logRange(buffer,5, 8);;
        logPosition(buffer);    // 13, Long is 64 bits, 8 bytes

        // put an array of bytes
        buffer.put(new byte[] {1,2,3,4,5});
        logRange(buffer, 13, 5);
        logPosition(buffer);    // 18

        // skip a couple of positions and add another byte
        buffer.put(20, Byte.MAX_VALUE);
        logPosition(buffer);    // 18, put operations specifying the index do not move the position

        logRange(buffer, 0, 30);
    }

    private void logPosition(ByteBuffer buffer) {
        log.info("position: {}", buffer.position());
    }

    private void logRange(ByteBuffer buffer, int start, int length) {
        byte[] slice = new byte[length];

        buffer.get(start, slice);

        StringBuilder sb = new StringBuilder();
        for (byte b : slice) {
            sb.append(String.format("0x%02X ", b));
        }

        log.info("Bytes at offset {} to {}: [ {}] {}", start, start + length - 1, sb.toString(), padTo8(new BigInteger(slice).toString(2)));
    }

    private void logAll(ByteBuffer buffer) {
        for(int i = 0; i < buffer.limit(); i++) {
            log.info("Byte {}, val {}, 0b{}", i, buffer.get(i), Integer.toBinaryString(buffer.get(i)));
        }
    }

    private String padTo8(String bin) {
        int cnt = 8 - bin.length()%8;
        String str = ("0".repeat(cnt)+bin).replaceAll("(.{8})", "$1-");
        return str.substring(0, str.length()-1);
    }
}
