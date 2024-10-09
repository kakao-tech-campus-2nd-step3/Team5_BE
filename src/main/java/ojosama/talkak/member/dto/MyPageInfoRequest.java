package ojosama.talkak.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;

public record MyPageInfoRequest(
    @NotBlank(message = "성별 정보를 올바르게 입력해주세요.")
    String gender,
    @NotBlank(message = "나이 정보를 올바르게 입력해주세요.")
    String age,
    @NotEmpty(message = "카테고리 정보를 올바르게 입력해주세요.")
    @Size(min = 3, max = 3, message = "카테고리는 3개를 선택해야 합니다.")
    List<Long> categories
) {


}
