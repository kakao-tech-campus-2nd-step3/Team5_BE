package ojosama.talkak.category.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter @Setter
public class MemberCategoryId implements Serializable {

    @Column(name = "member_id")
    private Long memberId;
    @Column(name = "category_id")
    private Long categoryId;

}
