package v.budget.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Table(name = "operation")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @JdbcTypeCode(SqlTypes.BIGINT)
    private Long id;
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Column(name = "category_name", nullable = false)
    private String categoryName;
    @Column(name = "amount", nullable = false)
    @JdbcTypeCode(SqlTypes.FLOAT)
    private Double amount;
    @Column(name = "type", nullable = false)
    private String type;
}
