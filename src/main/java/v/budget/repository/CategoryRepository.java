package v.budget.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import v.budget.domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByUserIdAndName(Long userId, String name);

}
