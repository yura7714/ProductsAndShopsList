package ru.krutikov.products_and_shops_list.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.krutikov.products_and_shops_list.dto.MonthlyExpenseDto;
import ru.krutikov.products_and_shops_list.service.ExpenseService;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("/monthly")
    public String getMonthlyExpenses(@RequestParam(required = false) Integer year,
                                     Model model) {
        if (year == null) {
            year = LocalDate.now().getYear();
        }

        List<MonthlyExpenseDto> monthlyExpenses = expenseService.getYearlyExpenses(year);
        Double totalYear = monthlyExpenses.stream()
                .mapToDouble(MonthlyExpenseDto::getTotal)
                .sum();

        model.addAttribute("monthlyExpenses", monthlyExpenses);
        model.addAttribute("totalYear", totalYear);
        model.addAttribute("selectedYear", year);
        model.addAttribute("currentYear", LocalDate.now().getYear());

        return "expenses/monthly";
    }

    @GetMapping("/last-year")
    @ResponseBody
    public List<MonthlyExpenseDto> getLast12MonthsExpenses() {
        return expenseService.getLast12MonthsExpenses();
    }
}
