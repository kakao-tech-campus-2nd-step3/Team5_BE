package ojosama.talkak.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;
import ojosama.talkak.common.exception.MyPageEx;
import ojosama.talkak.common.exception.TalKakException;
import ojosama.talkak.common.exception.code.MemberError;

@MyPageEx(exceptionType = TalKakException.class, errorCode = MemberError.ERROR_UPDATE_MEMBER_INFO)
public record MyPageInfoRequest(
    @NotBlank
    String gender,
    @NotBlank
    String age,
    @NotEmpty @Size(min = 3, max = 3)
    List<Long> categories
) {


}
