package ojosama.talkak.auth.service;

import lombok.RequiredArgsConstructor;
import ojosama.talkak.auth.dto.GoogleUserDetails;
import ojosama.talkak.auth.dto.OAuth2UserDetails;
import ojosama.talkak.member.model.Member;
import ojosama.talkak.member.repository.MemberRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

@RequiredArgsConstructor
public class AuthService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final DefaultOAuth2UserService defaultOAuth2UserService;
    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = defaultOAuth2UserService.loadUser(userRequest);
        GoogleUserDetails userDetails = GoogleUserDetails.from(oAuth2User.getAttributes());

        return OAuth2UserDetails.of(memberRepository.findByEmail(userDetails.email())
            .orElse(register(userDetails)));
    }

    private Member register(GoogleUserDetails userDetails) {
        return memberRepository.save(userDetails.toEntity());
    }
}
