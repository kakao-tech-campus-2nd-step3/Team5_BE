package ojosama.talkak.auth.dto;

import java.util.Map;
import ojosama.talkak.member.domain.Member;

public record GoogleUserDetails(
    String email,
    String name,
    String picture
) {

    public static GoogleUserDetails from(Map<String, Object> attributes) {
        return new GoogleUserDetails((String) attributes.get("email"),
            (String) attributes.get("name"),
            (String) attributes.get("picture"));
    }

    public Member toEntity() {
        return Member.of(name, picture, email);
    }
}
