package ru.krutikov.products_and_shops_list.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.krutikov.products_and_shops_list.entity.Product;
import ru.krutikov.products_and_shops_list.entity.ProductList;
import ru.krutikov.products_and_shops_list.entity.ProductListProduct;
import ru.krutikov.products_and_shops_list.repository.ProductListProductRepository;
import ru.krutikov.products_and_shops_list.repository.ProductListRepository;
import ru.krutikov.products_and_shops_list.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class ProductListService {
    private final ProductListRepository productListRepository;
    private final ProductListProductRepository productListProductRepository;
    private final ProductRepository productRepository;

    public ProductListService(ProductListRepository productListRepository,
                              ProductListProductRepository productListProductRepository,
                              ProductRepository productRepository) {
        this.productListRepository = productListRepository;
        this.productListProductRepository = productListProductRepository;
        this.productRepository = productRepository;
    }

    public void saveProductList(ProductList productList) {
        log.info("Сохранение списка покупок: {}", productList.getName());

        if (productList.getId() != null) {
            // Удаляем старые связи
            productListProductRepository.deleteByProductListId(productList.getId());
        }

        for (ProductListProduct productListProduct : productList.getProducts()) {

            // если на форме удалить продукты из начала списка, то автоматом в products попадают null элементы, пропускаем их
            if (productListProduct.getProduct() == null || productListProduct.getProduct().getId() == null) {
                continue;
            }

            Product product = productRepository.findById(productListProduct.getProduct().getId()).get();

            productListProduct.setId(null);
            productListProduct.setProduct(product);
            productListProduct.setProductList(productList);
        }

        productListRepository.save(productList);
    }

    public List<ProductList> findAll() {
        log.info("Поиск всех списков покупок всех пользователей");
        return productListRepository.findAll();
    }

    public List<ProductList> findAllByUser(String username) {
        log.info("Поиск списков покупок пользователя: {}", username);
        return productListRepository.findByCreatedByUsername(username);
    }

    public void deleteById(Long productListId) {
        log.info("Удаление списка покупок с id: {}", productListId);
        productListProductRepository.deleteByProductListId(productListId);
        productListRepository.deleteById(productListId);
    }

    public Optional<ProductList> findById(Long productListId) {
        log.info("Поиск списка покупок по id: {}", productListId);
        return productListRepository.findById(productListId);
    }
}
