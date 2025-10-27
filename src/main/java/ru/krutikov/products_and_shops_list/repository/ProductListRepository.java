package ru.krutikov.products_and_shops_list.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.krutikov.products_and_shops_list.entity.ProductList;

public interface ProductListRepository extends JpaRepository<ProductList, Long> {
}
