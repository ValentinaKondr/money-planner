package v.budget.service;

import v.budget.api.dto.CategoryDto;
import v.budget.api.dto.OperationDto;
import v.budget.api.dto.SummaryDto;
import v.budget.api.dto.UserDto;

public interface BudgetService {
    SummaryDto getSummary(Long userId);

    CategoryDto create(CategoryDto dto);

    OperationDto create(OperationDto dto);

    UserDto register(UserDto dto);
}
