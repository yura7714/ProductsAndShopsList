package ru.krutikov.products_and_shops_list.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.krutikov.products_and_shops_list.entity.Shop;
import ru.krutikov.products_and_shops_list.service.ShopService;

import java.util.List;

@Controller
public class ShopController {
    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping("/shops")
    public String shops(Model model) {
        List<Shop> shops = shopService.findAll();
        model.addAttribute("shops", shops);
        return "list-shops";
    }

    @GetMapping("/addShop")
    public ModelAndView addShop() {
        ModelAndView mav = new ModelAndView("update-shop-form");
        Shop shop = new Shop();
        mav.addObject("shop", shop);
        return mav;
    }

    @PostMapping("/saveShop")
    public String saveShop(@ModelAttribute Shop shop) {
        shopService.saveShop(shop);
        return "redirect:/shops";
    }
}
