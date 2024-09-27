package ojosama.talkak.category.repository;

import java.util.List;
import ojosama.talkak.category.model.Category;
import ojosama.talkak.category.model.MemberCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberCategoryRepository extends JpaRepository<MemberCategory, Long> {

    @Query("select mc.category from MemberCategory mc where mc.member.id = :memberId")
    List<Category> findAllCategoriesByMember(@Param("memberId") Long memberId);

    List<MemberCategory> findAllByMemberId(Long memberId);
}
