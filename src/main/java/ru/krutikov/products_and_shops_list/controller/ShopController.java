package ru.krutikov.products_and_shops_list.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.krutikov.products_and_shops_list.entity.Shop;
import ru.krutikov.products_and_shops_list.entity.User;
import ru.krutikov.products_and_shops_list.service.ShopService;
import ru.krutikov.products_and_shops_list.service.UserService;

import java.util.List;

@Controller
public class ShopController {
    private final ShopService shopService;
    private final UserService userService;

    public ShopController(ShopService shopService,
                          UserService userService) {
        this.shopService = shopService;
        this.userService = userService;
    }

    @GetMapping("/shops")
    public String shops(Model model) {
        List<Shop> shops;
//        if (securityContextHolderAwareRequestWrapper.isUserInRole("ROLE_ADMIN")) {
            shops = shopService.findAll();
//        } else {
//
//        }
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
    public String saveShop(@ModelAttribute Shop shop,
                           @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        User currentUser = userService.findUserByEmail(username);

        shop.setCreatedBy(currentUser);

        shopService.saveShop(shop);
        return "redirect:/shops";
    }
}
