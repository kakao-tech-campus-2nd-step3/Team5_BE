package ojosama.talkak.auth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(
    String secretKey,
    Long accessTokenExpireIn,
    Long refreshTokenExpireIn
) {}
