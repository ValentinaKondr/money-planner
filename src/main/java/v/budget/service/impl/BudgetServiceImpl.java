package v.budget.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import v.budget.api.dto.*;
import v.budget.api.dto.exception.ResourceNotFoundException;
import v.budget.domain.Category;
import v.budget.domain.Operation;
import v.budget.domain.Summary;
import v.budget.domain.User;
import v.budget.repository.CategoryRepository;
import v.budget.repository.OperationRepository;
import v.budget.repository.UserRepository;
import v.budget.service.BudgetService;

import java.util.List;

@Service
public class BudgetServiceImpl implements BudgetService {

    public static final String EXPENSE = "EXPENSE";
    public static final String INCOME = "INCOME";
    private static final Logger logger = LoggerFactory.getLogger(BudgetServiceImpl.class);
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final OperationRepository operationRepository;

    public BudgetServiceImpl(UserRepository userRepository,
                             CategoryRepository categoryRepository,
                             OperationRepository operationRepository) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.operationRepository = operationRepository;
    }

    @Override
    @Transactional
    public UserDto register(UserDto dto) {

        User savedUser = userRepository.save(
                User.builder()
                        .login(dto.getLogin())
                        .password(dto.getPassword())
                        .build());

        return UserDto.builder()
                .id(savedUser.getId())
                .login(savedUser.getLogin())
                .password(savedUser.getPassword())
                .build();
    }

    @Override
    @Transactional
    public CategoryDto create(CategoryDto dto) {
        if (!userRepository.existsById(dto.getUserId())) {
            String message = String.format("Пользователь не найден: %s", dto.getUserId().toString());
            logger.debug(message);
            throw new ResourceNotFoundException(message);
        }
        Category savedCategory = categoryRepository.save(
                Category.builder()
                        .userId(dto.getUserId())
                        .name(dto.getName())
                        .limit(dto.getLimit())
                        .build()
        );
        return CategoryDto.builder()
                .id(savedCategory.getId())
                .userId(dto.getUserId())
                .name(dto.getName())
                .limit(dto.getLimit())
                .build();
    }

    @Override
    @Transactional
    public OperationDto create(OperationDto dto) {
        if (!userRepository.existsById(dto.getUserId())) {
            String message = String.format("Пользователь не найден: %s", dto.getUserId().toString());
            logger.debug(message);
            throw new ResourceNotFoundException(message);
        }
        if (!categoryRepository.existsByUserIdAndName(dto.getUserId(), dto.getCategoryName())) {
            String message = String.format("Категория не найдена: %s", dto.getCategoryName());
            logger.debug(message);
            throw new ResourceNotFoundException(message);
        }

        Operation savedOperation = operationRepository.save(
                Operation.builder()
                        .userId(dto.getUserId())
                        .categoryName(dto.getCategoryName())
                        .amount(dto.getAmount())
                        .type(dto.getType())
                        .build());

        if (savedOperation.getType().equals(EXPENSE)) {
            Summary categorySummary =
                    operationRepository.getCategorySummaryByUserIdAndName(dto.getUserId(), dto.getCategoryName());
            if (categorySummary.getLimit() < categorySummary.getAmount()) {
                logger.info("Пользователь {} превысил лимит на категорию {}, суммарная трата - {}, лимит - {}",
                        dto.getUserId(), dto.getCategoryName(),
                        categorySummary.getAmount(), categorySummary.getLimit()
                );
            }
        }

        return OperationDto.builder()
                .id(savedOperation.getId())
                .userId(savedOperation.getUserId())
                .categoryName(savedOperation.getCategoryName())
                .amount(savedOperation.getAmount())
                .type(savedOperation.getType())
                .build();
    }

    @Override
    public SummaryDto getSummary(Long userId) {
        if (!userRepository.existsById(userId)) {
            String message = String.format("Пользователь не найден: %s", userId);
            logger.info(message);
            throw new ResourceNotFoundException(message);
        }

        List<Summary> summaries = operationRepository.getSummaryByUserId(userId);

        List<Summary> incomeSummaries = summaries.stream().filter(it -> it.getType().equals(INCOME)).toList();
        List<Summary> expenseSummaries = summaries.stream().filter(it -> it.getType().equals(EXPENSE)).toList();

        return SummaryDto.builder()
                .totalIncome(incomeSummaries.stream().mapToDouble(Summary::getAmount).sum())
                .totalExpense(expenseSummaries.stream().mapToDouble(Summary::getAmount).sum())
                .incomes(incomeSummaries.stream().map(it ->
                                IncomeDto.builder()
                                        .name(it.getCategoryName())
                                        .amount(it.getAmount())
                                        .build()
                        ).toList()
                )
                .expenses(expenseSummaries.stream().map(it ->
                                ExpenseDto.builder()
                                        .name(it.getCategoryName())
                                        .amount(it.getAmount())
                                        .limit(it.getLimit())
                                        .build()
                        ).toList()
                )
                .build();
    }
}
