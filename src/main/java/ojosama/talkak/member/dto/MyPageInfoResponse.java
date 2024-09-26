package ojosama.talkak.member.dto;

import java.util.ArrayList;
import java.util.List;
import ojosama.talkak.category.model.Category;
import ojosama.talkak.member.model.Member;

public record MyPageInfoResponse(
    String gender,
    Integer age,
    List<String> categories
) {

    public static MyPageInfoResponse of(Member member, List<Category> categories) {
        String gender = !member.getGender() ? "남자" : "여자";
        Integer age = member.getAge();
        List<String> names = categories.stream()
            .map(Category::getCategory)
            .toList();
        return new MyPageInfoResponse(gender, age, names);
    }

}
