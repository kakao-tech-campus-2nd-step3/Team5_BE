package ojosama.talkak.auth.config;

import lombok.RequiredArgsConstructor;
import ojosama.talkak.auth.filter.JwtAuthorizationFilter;
import ojosama.talkak.auth.filter.SuccessHandler;
import ojosama.talkak.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthService authService;
    private final SuccessHandler successHandler;
    private final JwtAuthorizationFilter jwtAuthorizationFilter;
    private final AuthProperties authProperties;

    @Value("${springdoc.swagger-ui.path}")
    private String swaggerAlias;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .headers(
                (headers) ->
                    headers.frameOptions(FrameOptionsConfig::disable)
            )
            .csrf(
                AbstractHttpConfigurer::disable
            )
            .cors(
                (cors) ->
                    cors.configurationSource(corsConfigurationSource())
            )
            .authorizeHttpRequests(
                (authorizeRequests) -> authorizeRequests
                    .requestMatchers("/h2-console/**").permitAll()
                    .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", swaggerAlias).permitAll()
                    .requestMatchers(authProperties.authorizationUri()).permitAll()
                    .anyRequest().authenticated()
            )
            .oauth2Login((oauth2Login) -> oauth2Login
                .authorizationEndpoint(endpoint -> endpoint
                    .baseUri(authProperties.authorizationUri()))
                .redirectionEndpoint(endpoint -> endpoint
                    .baseUri(authProperties.redirectionUri()))
                .userInfoEndpoint(endpoint -> endpoint
                    .userService(authService))
                .successHandler(successHandler)
            )
            .addFilterBefore(jwtAuthorizationFilter, AuthorizationFilter.class)
            .httpBasic(
                AbstractHttpConfigurer::disable
            )
            .formLogin(
                AbstractHttpConfigurer::disable
            )
        ;

        return http.build();
    }

    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addExposedHeader("*");
        corsConfiguration.addAllowedOriginPattern("*");
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
