package ojosama.talkak.category.domain;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CategoryType {
    MUSIC("음악"), JOURNEY("여행"), GAME("게임"), SPORT("스포츠"), FOOD("음식");

    private final String name;

    public static CategoryType fromName(String name) {
        return Arrays.stream(CategoryType.values())
            .filter(categoryType -> categoryType.getName().equals(name))
            .findFirst()
            .orElse(null);
    }

}
