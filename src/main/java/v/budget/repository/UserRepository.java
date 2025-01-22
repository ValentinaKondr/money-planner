package v.budget.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import v.budget.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsById(Long id);
}
