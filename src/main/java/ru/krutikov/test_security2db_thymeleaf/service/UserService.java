package ru.krutikov.test_security2db_thymeleaf.service;

import ru.krutikov.test_security2db_thymeleaf.dto.UserDto;
import ru.krutikov.test_security2db_thymeleaf.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();
}
