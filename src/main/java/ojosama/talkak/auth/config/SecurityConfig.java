package ojosama.talkak.auth.config;

import lombok.RequiredArgsConstructor;
import ojosama.talkak.auth.service.AuthService;
import ojosama.talkak.auth.service.SuccessHandler;
import ojosama.talkak.member.repository.MemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthService authService;
    private final SuccessHandler successHandler;

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
                    // TODO
                    .anyRequest().permitAll()
            )
            .oauth2Login((oauth2Login) -> oauth2Login
                .userInfoEndpoint((userInfoEndpoint) -> userInfoEndpoint
                    .userService(authService))
            )
            .httpBasic(
                AbstractHttpConfigurer::disable
            )
            .formLogin(
                AbstractHttpConfigurer::disable
            )
        ;

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
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

    @Bean
    public DefaultOAuth2UserService defaultOAuth2UserService() {
        return new DefaultOAuth2UserService();
    }

    @Bean
    public AuthService authService(DefaultOAuth2UserService defaultOAuth2UserService,
        MemberRepository memberRepository) {

        return new AuthService(defaultOAuth2UserService, memberRepository);
    }

    @Bean
    public SuccessHandler successHandler() {
        return new SuccessHandler();
    }
}
