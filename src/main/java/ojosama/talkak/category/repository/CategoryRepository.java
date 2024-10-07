package ojosama.talkak.category.repository;
import java.util.Optional;
import ojosama.talkak.category.domain.Category;
import ojosama.talkak.category.domain.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByCategoryType(CategoryType categoryType);
    String findCategoryById(Long categoryId);

}
