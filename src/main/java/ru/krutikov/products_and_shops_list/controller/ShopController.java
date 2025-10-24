package ru.krutikov.products_and_shops_list.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShopController {
    @GetMapping("/shops")
    public String shops() {
        return "list-shops";
    }
}
