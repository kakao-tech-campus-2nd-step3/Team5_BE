package ojosama.talkak.member.dto;

import java.util.List;
import ojosama.talkak.category.model.Category;
import ojosama.talkak.member.model.Member;

public record MyPageInfoResponse(
    String gender,
    String age,
    List<CategoryResponse> categories
) {

    public static MyPageInfoResponse of(Member member, List<Category> categories) {
        String gender = !member.getGender() ? "남자" : "여자";
        String age = member.getAge().getName();
        List<CategoryResponse> categoryResponses = categories.stream()
            .map(c -> new CategoryResponse(c.getId(), c.getCategoryType().getName()))
            .toList();
        return new MyPageInfoResponse(gender, age, categoryResponses);
    }
}

