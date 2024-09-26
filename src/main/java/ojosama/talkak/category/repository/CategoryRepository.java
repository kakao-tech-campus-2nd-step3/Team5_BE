package ojosama.talkak.category.repository;

import ojosama.talkak.category.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    String findCategoryById(Long categoryId);
}
