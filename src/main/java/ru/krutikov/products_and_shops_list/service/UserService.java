package ru.krutikov.products_and_shops_list.service;

import ru.krutikov.products_and_shops_list.dto.UserDto;
import ru.krutikov.products_and_shops_list.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();
}
