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

        productList = productListRepository.save(productList);

        for (ProductListProduct productListProduct : productList.getProducts()) {

            Product product = productRepository.findById(productListProduct.getId()).get();

            productListProduct.setId(null);
            productListProduct.setProduct(product);
            productListProduct.setProductList(productList);
            productListProductRepository.save(productListProduct);
        }
    }

    public List<ProductList> findAll() {
        return productListRepository.findAll();
    }
}
