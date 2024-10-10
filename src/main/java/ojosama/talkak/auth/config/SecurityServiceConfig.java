package ojosama.talkak.auth.config;

import ojosama.talkak.auth.service.AuthService;
import ojosama.talkak.member.repository.MemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;

@Configuration
public class SecurityServiceConfig {

    @Bean
    public DefaultOAuth2UserService defaultOAuth2UserService() {
        return new DefaultOAuth2UserService();
    }

    @Bean
    public AuthService authService(DefaultOAuth2UserService defaultOAuth2UserService, MemberRepository memberRepository) {
        return new AuthService(defaultOAuth2UserService, memberRepository);
    }
}
