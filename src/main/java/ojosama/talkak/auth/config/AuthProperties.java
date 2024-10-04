package ojosama.talkak.auth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "auth")
public record AuthProperties(
    String authorizationUri,
    String redirectionUri
) {}
