package ojosama.talkak.category.repository;
import java.util.Optional;
import ojosama.talkak.category.model.Category;
import ojosama.talkak.category.model.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByCategoryType(CategoryType categoryType);
    String findCategoryById(Long categoryId);

}
