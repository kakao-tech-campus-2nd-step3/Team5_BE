package ojosama.talkak.member.dto;

import java.util.List;

public record AdditionalInfoRequest(
    List<String> categories,
    String age,
    String gender
) {}
