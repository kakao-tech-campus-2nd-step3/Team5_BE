package ojosama.talkak.member.dto;

import java.util.List;

public record MyPageInfoRequest(
    String gender,
    Integer age,
    List<String> categories
) {


}
