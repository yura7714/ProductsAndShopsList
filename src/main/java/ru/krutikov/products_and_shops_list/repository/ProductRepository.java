package ru.krutikov.products_and_shops_list.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.krutikov.products_and_shops_list.entity.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCreatedByUsername(String username);
}
