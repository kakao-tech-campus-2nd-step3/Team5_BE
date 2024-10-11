package ojosama.talkak.category.repository;
import java.util.Optional;
import ojosama.talkak.category.domain.Category;
import ojosama.talkak.category.domain.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByCategoryType(CategoryType categoryType);
    @Query("SELECT c.categoryType FROM Category c WHERE c.id = :id")
    Optional<CategoryType> findCategoryTypeById(@Param("id") Long id);

}
