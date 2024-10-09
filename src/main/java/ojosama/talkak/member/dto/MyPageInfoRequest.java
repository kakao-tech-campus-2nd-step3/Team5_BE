package ojosama.talkak.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;

public record MyPageInfoRequest(
    @NotBlank
    String gender,
    @NotBlank
    String age,
    @NotEmpty @Size(min = 3, max = 3)
    List<Long> categories
) {


}
