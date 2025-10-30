package ru.krutikov.products_and_shops_list.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.krutikov.products_and_shops_list.dto.ProductDto;
import ru.krutikov.products_and_shops_list.dto.QuickProductRequest;
import ru.krutikov.products_and_shops_list.service.ProductService;

@Slf4j
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/quick-create")
    public ResponseEntity<ProductDto> quickCreateProduct(@RequestBody QuickProductRequest request,
                                                         @AuthenticationPrincipal UserDetails userDetails) {
        log.info("POST /api/products/quick-create - создание продукта {} пользователем: {}", request.getName(), userDetails.getUsername());
        ProductDto newProduct = productService.quickCreate(request, userDetails.getUsername());
        return ResponseEntity.ok(newProduct);
    }
}