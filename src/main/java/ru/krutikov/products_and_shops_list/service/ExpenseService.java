package ru.krutikov.products_and_shops_list.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.krutikov.products_and_shops_list.dto.MonthlyExpenseDto;
import ru.krutikov.products_and_shops_list.repository.ProductListRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ExpenseService {

    private final ProductListRepository productListRepository;

    public ExpenseService(ProductListRepository productListRepository) {
        this.productListRepository = productListRepository;
    }

    // Расходы за последние 12 месяцев
    public List<MonthlyExpenseDto> getLast12MonthsExpenses() {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusMonths(11).withDayOfMonth(1);

        List<Object[]> results = productListRepository.getMonthlyExpenses(startDate, endDate);
        return mapToMonthlyExpenseDto(results);
    }

    // Расходы за конкретный год
    public List<MonthlyExpenseDto> getYearlyExpenses(int year) {
        List<Object[]> results = productListRepository.getMonthlyExpensesForYear(year);
        List<MonthlyExpenseDto> expenses = mapToMonthlyExpenseDto(results);

        // Заполняем все месяцы (даже с нулевыми расходами)
        return fillMissingMonths(expenses, year);
    }

    // Расходы за текущий год
    public List<MonthlyExpenseDto> getCurrentYearExpenses() {
        return getYearlyExpenses(LocalDate.now().getYear());
    }

    // Суммарные расходы по месяцам
    public Map<String, Double> getMonthlyTotals(int year) {
        List<MonthlyExpenseDto> expenses = getYearlyExpenses(year);
        return expenses.stream()
                .collect(Collectors.toMap(
                        MonthlyExpenseDto::getMonthName,
                        MonthlyExpenseDto::getTotal
                ));
    }

    // Общая сумма за период
    public Double getTotalExpensesForPeriod(LocalDate startDate, LocalDate endDate) {
        List<Object[]> results = productListRepository.getMonthlyExpenses(startDate, endDate);
        return results.stream()
                .mapToDouble(result -> (Double) result[2])
                .sum();
    }

    private List<MonthlyExpenseDto> mapToMonthlyExpenseDto(List<Object[]> results) {
        return results.stream()
                .map(result -> {
                    if (result.length == 3) {
                        int year = ((Number) result[0]).intValue();
                        int month = ((Number) result[1]).intValue();
                        Double total = (Double) result[2];
                        return new MonthlyExpenseDto(year, month, total);
                    } else {
                        int month = ((Number) result[0]).intValue();
                        Double total = (Double) result[1];
                        return new MonthlyExpenseDto(LocalDate.now().getYear(), month, total);
                    }
                })
                .collect(Collectors.toList());
    }

    private List<MonthlyExpenseDto> fillMissingMonths(List<MonthlyExpenseDto> expenses, int year) {
        Map<Integer, MonthlyExpenseDto> expenseMap = expenses.stream()
                .collect(Collectors.toMap(MonthlyExpenseDto::getMonth, Function.identity()));

        List<MonthlyExpenseDto> allMonths = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            if (expenseMap.containsKey(month)) {
                allMonths.add(expenseMap.get(month));
            } else {
                allMonths.add(new MonthlyExpenseDto(year, month, 0.0));
            }
        }
        return allMonths;
    }
}
