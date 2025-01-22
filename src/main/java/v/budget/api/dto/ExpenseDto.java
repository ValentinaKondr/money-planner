package v.budget.api.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseDto {
    String name;
    Double limit;
    Double amount;
}
