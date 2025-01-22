package v.budget.api;

import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.*;
import v.budget.api.dto.CategoryDto;
import v.budget.api.dto.OperationDto;
import v.budget.api.dto.SummaryDto;
import v.budget.api.dto.UserDto;
import v.budget.service.BudgetService;

@RestController
@RequestMapping("api/v1/budget")
public class BudgetController {

    private final BudgetService budgetService;

    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @GetMapping
    public SummaryDto getSummary(@PathParam("userId") Long userId) {
        return budgetService.getSummary(userId);
    }

    @PostMapping("/category")
    public CategoryDto addCategory(@RequestBody CategoryDto dto) {
        return budgetService.create(dto);
    }

    @PostMapping("/operation")
    public OperationDto addOperation(@RequestBody OperationDto dto) {
        return budgetService.create(dto);
    }

    @PostMapping("/register")
    public UserDto register(@RequestBody UserDto dto) {
        return budgetService.register(dto);
    }
}
