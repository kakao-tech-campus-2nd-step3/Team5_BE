package ojosama.talkak.member.dto;

import ojosama.talkak.category.domain.Category;

public record CategoryResponse(
    Long id,
    String name
) {

    public static CategoryResponse from(Category category) {
        return new CategoryResponse(category.getId(), category.getCategoryType().getName());
    }
}