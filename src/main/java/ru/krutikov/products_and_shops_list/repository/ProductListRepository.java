package ru.krutikov.products_and_shops_list.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.krutikov.products_and_shops_list.entity.ProductList;

import java.time.LocalDate;
import java.util.List;

public interface ProductListRepository extends JpaRepository<ProductList, Long> {
    @Query("SELECT YEAR(pl.date) as year, MONTH(pl.date) as month, SUM(pl.totalPrice) as total " +
            "FROM ProductList pl " +
            "WHERE pl.date BETWEEN :startDate AND :endDate " +
            "GROUP BY YEAR(pl.date), MONTH(pl.date) " +
            "ORDER BY year DESC, month DESC")
    List<Object[]> getMonthlyExpenses(@Param("startDate") LocalDate startDate,
                                      @Param("endDate") LocalDate endDate);

    // Альтернативный вариант с конкретным годом
    @Query("SELECT MONTH(pl.date) as month, SUM(pl.totalPrice) as total " +
            "FROM ProductList pl " +
            "WHERE YEAR(pl.date) = :year " +
            "GROUP BY MONTH(pl.date) " +
            "ORDER BY month")
    List<Object[]> getMonthlyExpensesForYear(@Param("year") int year);
}
