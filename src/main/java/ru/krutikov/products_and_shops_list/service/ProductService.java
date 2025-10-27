package ru.krutikov.products_and_shops_list.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.krutikov.products_and_shops_list.dto.ProductDto;
import ru.krutikov.products_and_shops_list.dto.QuickProductRequest;
import ru.krutikov.products_and_shops_list.entity.Product;
import ru.krutikov.products_and_shops_list.repository.ProductRepository;

import java.util.List;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductDto quickCreate(QuickProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());

        Product savedProduct = productRepository.save(product);

        return mapToDto(savedProduct);
    }

    private ProductDto mapToDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        return dto;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
