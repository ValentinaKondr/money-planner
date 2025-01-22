package v.budget.api.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IncomeDto {
    String name;
    Double amount;
}
