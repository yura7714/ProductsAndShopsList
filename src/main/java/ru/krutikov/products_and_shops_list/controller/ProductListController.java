package ru.krutikov.products_and_shops_list.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductListController {
    @GetMapping("/productLists")
    public String productLists() {
        return "list-product-lists";
    }

}
