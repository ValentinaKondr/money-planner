package v.budget.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SummaryDto {
    Double totalIncome;
    Double totalExpense;
    List<IncomeDto> incomes;
    List<ExpenseDto> expenses;

}
