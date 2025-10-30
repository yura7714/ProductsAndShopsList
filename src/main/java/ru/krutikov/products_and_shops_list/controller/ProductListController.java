package ru.krutikov.products_and_shops_list.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.krutikov.products_and_shops_list.entity.Product;
import ru.krutikov.products_and_shops_list.entity.ProductList;
import ru.krutikov.products_and_shops_list.entity.Shop;
import ru.krutikov.products_and_shops_list.entity.User;
import ru.krutikov.products_and_shops_list.service.ProductListService;
import ru.krutikov.products_and_shops_list.service.ProductService;
import ru.krutikov.products_and_shops_list.service.ShopService;
import ru.krutikov.products_and_shops_list.service.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
public class ProductListController {
    private final ShopService shopService;
    private final ProductService productService;
    private final ProductListService productListService;
    private final UserService userService;

    public ProductListController(ShopService shopService,
                                 ProductService productService,
                                 ProductListService productListService,
                                 UserService userService) {

        this.shopService = shopService;
        this.productService = productService;
        this.productListService = productListService;
        this.userService = userService;
    }

    @GetMapping("/productLists")
    public ModelAndView productLists() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("GET /productLists - получение списков покупок пользователем: {}", authentication.getName());

        ModelAndView mav = new ModelAndView("list-product-lists");

        List<ProductList> productLists;
        if (authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {
            productLists = productListService.findAll();
        } else {
            productLists = productListService.findAllByUser(authentication.getName());
        }

        mav.addObject("productLists", productLists);

        return mav;
    }

    @GetMapping("/addProductList")
    public ModelAndView addProductList() {
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        log.info("GET /addProductList - получение формы добавления списка покупок пользователем: {}", username);

        ModelAndView mav = new ModelAndView("update-product-list");

        ProductList productList = new ProductList();
        productList.setDate(LocalDate.now());
        mav.addObject("productList", productList);

        List<Shop> userShops = shopService.findAllByUser(username);

        mav.addObject("shops", userShops);

        List<Product> products = productService.findAllByUser(username);
        mav.addObject("allProducts", products);

        return mav;
    }

    @PostMapping("/saveProductList")
    public String saveProductList(@ModelAttribute ProductList productList,
                                  @AuthenticationPrincipal UserDetails userDetails) {
        log.info("POST /saveProductList - сохранение списка покупок {} пользователем: {}", productList.getName(), userDetails.getUsername());

        String username = userDetails.getUsername();
        User currentUser = userService.findUserByUsername(username);

        productList.setCreatedBy(currentUser);

        productListService.saveProductList(productList);
        return "redirect:/productLists";
    }

    @GetMapping("/deleteProductList")
    public String deleteProductList(@RequestParam Long productListId,
                                    @AuthenticationPrincipal UserDetails userDetails) {
        log.info("GET /deleteProductList - удаление списка покупок с id {} пользователем {}", productListId, userDetails.getUsername());

        productListService.deleteById(productListId);
        return "redirect:/productLists";
    }

    @GetMapping("/updateProductList")
    public ModelAndView updateProductList(@RequestParam Long productListId,
                                          @AuthenticationPrincipal UserDetails userDetails) {
        log.info("GET /updateProductList - обновление списка покупок с id {} пользователем {}", productListId, userDetails.getUsername());
        ModelAndView mav = new ModelAndView("update-product-list");

        Optional<ProductList> optionalProductList = productListService.findById(productListId);
        ProductList productList = new ProductList();

        if (optionalProductList.isPresent()) {
            productList = optionalProductList.get();
        }

        mav.addObject("productList", productList);

        String username = productList.getCreatedBy().getUsername();
        List<Shop> userShops = shopService.findAllByUser(username);

        mav.addObject("shops", userShops);

        List<Product> products = productService.findAllByUser(username);
        mav.addObject("allProducts", products);

        return mav;
    }
}
