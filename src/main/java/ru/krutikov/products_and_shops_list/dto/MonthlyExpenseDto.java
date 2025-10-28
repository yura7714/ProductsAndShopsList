package ru.krutikov.products_and_shops_list.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MonthlyExpenseDto {
    private int year;
    private int month;
    private Double total;
    private String monthName;

    public MonthlyExpenseDto(int year, int month, Double total) {
        this.year = year;
        this.month = month;
        this.total = total;
        this.monthName = getMonthName(month);
    }

    private String getMonthName(int month) {
        return java.time.Month.of(month).getDisplayName(
                java.time.format.TextStyle.FULL_STANDALONE,
                new java.util.Locale("ru")
        );
    }
}
