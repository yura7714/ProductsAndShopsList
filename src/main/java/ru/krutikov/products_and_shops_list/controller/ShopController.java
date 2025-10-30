package ru.krutikov.products_and_shops_list.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.krutikov.products_and_shops_list.entity.Shop;
import ru.krutikov.products_and_shops_list.entity.User;
import ru.krutikov.products_and_shops_list.service.ShopService;
import ru.krutikov.products_and_shops_list.service.UserService;

import java.util.List;
import java.util.Optional;

@Slf4j
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("GET /shops - получение списка магазинов пользователем: {}", authentication.getName());

        List<Shop> shops;
        if (authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {
            shops = shopService.findAll();
        } else {
            shops = shopService.findAllByUser(authentication.getName());
        }
        model.addAttribute("shops", shops);
        return "list-shops";
    }

    @GetMapping("/addShop")
    public ModelAndView addShop(@AuthenticationPrincipal UserDetails userDetails) {
        log.info("GET /addShop - получение формы добавления магазина пользователем: {}", userDetails.getUsername());
        ModelAndView mav = new ModelAndView("update-shop-form");
        Shop shop = new Shop();
        mav.addObject("shop", shop);
        return mav;
    }

    @PostMapping("/saveShop")
    public String saveShop(@ModelAttribute Shop shop,
                           @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        log.info("POST /saveShop - сохранение магазина {} пользователем {}", shop.getName(), username);
        User currentUser = userService.findUserByUsername(username);

        shop.setCreatedBy(currentUser);

        shopService.saveShop(shop);
        return "redirect:/shops";
    }

    @GetMapping("/deleteShop")
    public String deleteShop(@RequestParam Long shopId,
                             @AuthenticationPrincipal UserDetails userDetails) {
        log.info("GET /deleteShop - удаление магазина с id {} пользователем {}", shopId, userDetails.getUsername());
        shopService.deleteById(shopId);
        return "redirect:/shops";
    }

    @GetMapping("/updateShop")
    public ModelAndView updateShop(@RequestParam Long shopId,
                                   @AuthenticationPrincipal UserDetails userDetails) {
        log.info("GET /updateShop - получение формы изменения магазина с id {} пользователем {}", shopId, userDetails.getUsername());
        ModelAndView mav = new ModelAndView("update-shop-form");
        Optional<Shop> optionalShop = shopService.findById(shopId);
        Shop shop = new Shop();
        if (optionalShop.isPresent()) {
            shop = optionalShop.get();
        }

        mav.addObject("shop", shop);
        return mav;
    }
}
