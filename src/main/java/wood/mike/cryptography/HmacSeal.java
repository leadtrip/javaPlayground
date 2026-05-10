package wood.mike.cryptography;

import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.params.KeyParameter;

public class HmacSeal {
    static void main() {
        new HmacSeal().run();
    }

    void run() {
        String itsoMessage = "";

    }

    public byte[] generateHmac(byte[] message, byte[] secretKey) {
        HMac hmac = new HMac(new SHA256Digest());
        hmac.init(new KeyParameter(secretKey));

        byte[] result = new byte[hmac.getMacSize()];
        hmac.update(message, 0, message.length);
        hmac.doFinal(result, 0);

        return result;
    }
}
