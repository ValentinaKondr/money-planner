package v.budget.domain;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Summary {
    String categoryName;
    Double amount;
    String type;
    Double limit;
}
