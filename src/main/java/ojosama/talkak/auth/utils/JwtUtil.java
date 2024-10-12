package ojosama.talkak.auth.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import ojosama.talkak.auth.config.JwtProperties;

public class JwtUtil {

    private final SecretKey secretKey;
    private final Long accessTokenExpireIn;
    private final Long refreshTokenExpireIn;

    public JwtUtil(JwtProperties jwtProperties) {
        this.secretKey = Keys.hmacShaKeyFor(jwtProperties.secretKey().getBytes());
        this.accessTokenExpireIn = jwtProperties.accessTokenExpireIn();
        this.refreshTokenExpireIn = jwtProperties.refreshTokenExpireIn();
    }

    public String generateAccessToken(String email, String username) {
        return generateToken(email, username, accessTokenExpireIn);
    }

    public String generateRefreshToken(String email, String username) {
        return generateToken(email, username, refreshTokenExpireIn);
    }

    private String generateToken(String email, String username, Long expiration) {
        long current = System.currentTimeMillis();
        return Jwts.builder()
            .claim("email", email)
            .claim("username", username)
            .issuedAt(new Date(current))
            .expiration(new Date(current + expiration))
            .signWith(secretKey)
            .compact();
    }
}
