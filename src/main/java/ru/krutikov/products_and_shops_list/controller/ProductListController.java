package ru.krutikov.products_and_shops_list.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.krutikov.products_and_shops_list.entity.Product;
import ru.krutikov.products_and_shops_list.entity.ProductList;
import ru.krutikov.products_and_shops_list.entity.Shop;
import ru.krutikov.products_and_shops_list.service.ProductListService;
import ru.krutikov.products_and_shops_list.service.ProductService;
import ru.krutikov.products_and_shops_list.service.ShopService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class ProductListController {
    private final ShopService shopService;
    private final ProductService productService;
    private final ProductListService productListService;

    public ProductListController(ShopService shopService,
                                 ProductService productService,
                                 ProductListService productListService) {

        this.shopService = shopService;
        this.productService = productService;
        this.productListService = productListService;
    }

    @GetMapping("/productLists")
    public ModelAndView productLists() {
        ModelAndView mav = new ModelAndView("list-product-lists");
        List<ProductList> productLists = productListService.findAll();

        mav.addObject("productLists", productLists);

        return mav;
    }

    @GetMapping("/addProductList")
    public ModelAndView addProductList() {
        ModelAndView mav = new ModelAndView("update-product-list");

        ProductList productList = new ProductList();
        productList.setDate(LocalDate.now());
        mav.addObject("productList", productList);

        List<Shop> userShops = shopService.findAllByUser(
                SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName()
        );

        mav.addObject("shops", userShops);

        List<Product> products = productService.getAllProducts();
        mav.addObject("allProducts", products);

        return mav;
    }

    @PostMapping("/saveProductList")
    public String saveProductList(@ModelAttribute ProductList productList) {
        productListService.saveProductList(productList);
        return "redirect:/productLists";
    }

    @GetMapping("/deleteProductList")
    public String deleteProductList(@RequestParam Long productListId) {
        productListService.deleteById(productListId);
        return "redirect:/productLists";
    }

    @GetMapping("/updateProductList")
    public ModelAndView updateProductList(@RequestParam Long productListId) {
        ModelAndView mav = new ModelAndView("update-product-list");

        Optional<ProductList> optionalProductList = productListService.findById(productListId);
        ProductList productList = new ProductList();

        if (optionalProductList.isPresent()) {
            productList = optionalProductList.get();
        }

        mav.addObject("productList", productList);

        List<Shop> userShops = shopService.findAllByUser(
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName()
        );

        mav.addObject("shops", userShops);

        List<Product> products = productService.getAllProducts();
        mav.addObject("allProducts", products);

        return mav;
    }
}
