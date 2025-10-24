package ru.krutikov.products_and_shops_list.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.krutikov.products_and_shops_list.entity.Role;
import ru.krutikov.products_and_shops_list.entity.User;
import ru.krutikov.products_and_shops_list.service.RoleService;
import ru.krutikov.products_and_shops_list.service.UserService;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {
    private final UserService userService;
    private final RoleService roleService;

    public UserController(UserService userService,
                          RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/showUpdateUserForm")
    public ModelAndView showUpdateForm(@RequestParam Long userId) {
        ModelAndView mav = new ModelAndView("update-user-form");
        Optional<User> optionalUser = userService.findById(userId);
        User user = new User();
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        }

        List<Role> roles = roleService.findAll();

        mav.addObject("roles", roles);
        mav.addObject("user", user);
        return mav;
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/users";
    }
}
