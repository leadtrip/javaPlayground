package wood.mike.anything;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

public class JwtParser {
    public static void main(String[] args) {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJ0YiIsImlzU3RhZmZNZW1iZXIiOnRydWUsImhhc0NyZWRpdHMiOmZhbHNlLCJpc3MiOiJzZSIsInVzZXJJZCI6ODMzMjYxNDgsImVtYWlsIjoibWlrZS53b29kQHNlY3JldGVzY2FwZXMuY29tIn0.R4zFTAHSciNlkF-KmHccRHLBeFOMPO9OZ9X8ycwwXxo";
        String secret = "SK2hTuImZpk1Qam5uOymtyAHQf9hcUGi";

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
