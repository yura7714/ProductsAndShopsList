package ru.krutikov.products_and_shops_list.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.krutikov.products_and_shops_list.entity.ProductListProduct;

import java.util.List;

@Repository
public interface ProductListProductRepository extends JpaRepository<ProductListProduct, Long> {
    void deleteByProductListId(Long productListId);
    List<ProductListProduct> findByProductListId(Long productListId);
}
