package ru.krutikov.products_and_shops_list.service;

import ru.krutikov.products_and_shops_list.dto.UserDto;
import ru.krutikov.products_and_shops_list.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void saveUser(UserDto userDto);

    void saveUser(User user);

    User findUserByUsername(String username);

    List<UserDto> findAllUsers();

    Optional<User> findById(Long userId);
}
