package ojosama.talkak.auth.dto;

import java.util.Collection;
import java.util.Map;
import ojosama.talkak.member.domain.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

public record OAuth2UserDetails(
    Long id,
    String email,
    String username,
    String imageUrl
) implements OAuth2User {

    public static OAuth2UserDetails of(Member member) {
        return new OAuth2UserDetails(member.getId(),
            member.getEmail(),
            member.getUsername(),
            member.getImageUrl());
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getName() {
        return username;
    }
}
