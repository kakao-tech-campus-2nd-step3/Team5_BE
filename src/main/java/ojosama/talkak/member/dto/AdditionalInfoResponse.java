package ojosama.talkak.member.dto;

import java.util.List;
import ojosama.talkak.category.domain.Category;

public record AdditionalInfoResponse(
    List<CategoryResponse> categories,
    String gender,
    String age
) {

    public static AdditionalInfoResponse of(List<Category> categories, AdditionalInfoRequest request) {
        return new AdditionalInfoResponse(
            categories.stream()
                .map(CategoryResponse::from)
                .toList(),
            request.gender(),
            request.age()
        );
    }
}
