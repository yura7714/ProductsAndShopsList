package ru.krutikov.products_and_shops_list.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.krutikov.products_and_shops_list.dto.ProductDto;
import ru.krutikov.products_and_shops_list.dto.QuickProductRequest;
import ru.krutikov.products_and_shops_list.entity.Product;
import ru.krutikov.products_and_shops_list.entity.User;
import ru.krutikov.products_and_shops_list.repository.ProductRepository;
import ru.krutikov.products_and_shops_list.repository.UserRepository;

import java.util.List;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ProductService(ProductRepository productRepository,
                          UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public ProductDto quickCreate(QuickProductRequest request, String username) {
        Product product = new Product();
        product.setName(request.getName());

        User productCreator = userRepository.findByUsername(username);

        product.setCreatedBy(productCreator);

        Product savedProduct = productRepository.save(product);

        return mapToDto(savedProduct);
    }

    private ProductDto mapToDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        return dto;
    }

    public List<Product> findAllByUser(String username) {
        return productRepository.findByCreatedByUsername(username);
    }
}
