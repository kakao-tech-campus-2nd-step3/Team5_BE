package ojosama.talkak.category.repository;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import ojosama.talkak.category.domain.Category;
import ojosama.talkak.category.domain.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByCategoryType(CategoryType categoryType);
    String findCategoryById(Long categoryId);
    @Query("SELECT c.id FROM Category c WHERE c.id IN :ids")
    Set<Long> findExistingIds(@Param("ids") Set<Long> ids);
    @Query("select c from Category c where c.id in :categoryIds")
    List<Category> findAllByCategoryIds(@Param("categoryIds") List<Long> categoryIds);

}
