package ru.krutikov.products_and_shops_list.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.krutikov.products_and_shops_list.entity.User;

@Slf4j
@Controller
public class AboutController {
    @GetMapping("/about")
    public String about(@AuthenticationPrincipal UserDetails userDetails) {
        log.info("GET /about - {} зашёл в информацию о приложении", userDetails.getUsername());
        return "about";
    }
}
