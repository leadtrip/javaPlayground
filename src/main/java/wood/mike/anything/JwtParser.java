package wood.mike.anything;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

public class JwtParser {
    public static void main(String[] args) {
        String token = "";
        String secret = "";

        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());

        try {
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            System.out.println(STR."Token is valid. Claims: \{claims}");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
