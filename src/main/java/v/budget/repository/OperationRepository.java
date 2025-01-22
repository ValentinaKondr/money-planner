package v.budget.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import v.budget.domain.Operation;
import v.budget.domain.Summary;

import java.util.List;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {
    @Query("SELECT new v.budget.domain.Summary(o.categoryName, SUM(o.amount), o.type, c.limit) " +
            "FROM Operation o " +
            "LEFT JOIN Category c ON o.categoryName = c.name " +
            "WHERE o.userId = :userId " +
            "GROUP BY o.categoryName, o.type, c.limit")
    List<Summary> getSummaryByUserId(Long userId);

    @Query("SELECT new v.budget.domain.Summary(o.categoryName, SUM(o.amount), o.type, c.limit) " +
            "FROM Operation o " +
            "LEFT JOIN Category c ON o.categoryName = c.name " +
            "WHERE o.userId = :userId AND o.categoryName = :categoryName " +
            "AND o.type = 'EXPENSE' " +
            "GROUP BY o.categoryName, o.type, c.limit")
    Summary getCategorySummaryByUserIdAndName(Long userId, String categoryName);

}
