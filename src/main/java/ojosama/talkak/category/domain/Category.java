package ojosama.talkak.category.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ojosama.talkak.common.exception.TalKakException;
import ojosama.talkak.common.exception.code.MemberError;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Table(name = "category")
public class Category {

    public static final Integer ALLOWED_CATEGORY_SELECT_COUNT = 3;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private CategoryType categoryType;

    public Category(CategoryType categoryType) {
        this.categoryType = categoryType;
    }

    public static void validateCategoryInputs(Set<Long> categories) {
        // 허용 카테고리 개수와 일치하지 않으면 잘못된 입력 요청
        if(categories.size() != ALLOWED_CATEGORY_SELECT_COUNT) {
            throw TalKakException.of(MemberError.ERROR_UPDATE_MEMBER_INFO);
        }
    }
}
