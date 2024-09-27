package ojosama.talkak.category.repository;
import java.util.Optional;
import ojosama.talkak.category.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);
    String findCategoryById(Long categoryId);

}
