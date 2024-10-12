package ojosama.talkak.member.dto;

import java.util.List;

public record MyPageInfoRequest(
    String gender,
    String age,
    List<Long> categories
) {


}
