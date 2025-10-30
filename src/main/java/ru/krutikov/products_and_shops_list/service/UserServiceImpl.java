package ru.krutikov.products_and_shops_list.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.krutikov.products_and_shops_list.dto.UserDto;
import ru.krutikov.products_and_shops_list.entity.Role;
import ru.krutikov.products_and_shops_list.entity.User;
import ru.krutikov.products_and_shops_list.repository.RoleRepository;
import ru.krutikov.products_and_shops_list.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserDto userDto) {
        log.info("Сохранение пользователя из регистрации: {}", userDto.getUsername());

        User user = new User();
        user.setUsername(userDto.getUsername());
        //encrypt the password using spring security
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Role role = roleRepository.findByName("ROLE_READ_ONLY");
        if (role == null) {
            role = checkRoleExist();
        }

        user.setRole(role);
        userRepository.save(user);
    }

    @Override
    public void saveUser(User user) {
        log.info("Сохранение пользователя из формы: {}", user.getUsername());

        userRepository.save(user);
    }

    private Role checkRoleExist() {
        Role role = new Role();
        role.setName("ROLE_READ_ONLY");
        return roleRepository.save(role);
    }

    @Override
    public User findUserByUsername(String username) {
        log.info("Поиск пользователя по логину: {}", username);
        return userRepository.findByUsername(username);
    }

    @Override
    public List<UserDto> findAllUsers() {
        log.info("Поиск всех пользователей");
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> findById(Long userId) {
        log.info("Поиск пользователя по id: {}", userId);
        return userRepository.findById(userId);
    }

    private UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setRole(user.getRole());
        return userDto;
    }
}
