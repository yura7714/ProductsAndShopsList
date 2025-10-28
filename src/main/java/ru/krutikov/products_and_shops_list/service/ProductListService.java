package ru.krutikov.products_and_shops_list.service;

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
        return productListRepository.findAll();
    }

    public List<ProductList> findAllByUser(String username) {
        return productListRepository.findByCreatedByUsername(username);
    }

    public void deleteById(Long productListId) {
        productListProductRepository.deleteByProductListId(productListId);
        productListRepository.deleteById(productListId);
    }

    public Optional<ProductList> findById(Long productListId) {
        return productListRepository.findById(productListId);
    }
}
