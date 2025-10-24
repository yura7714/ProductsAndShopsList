package ru.krutikov.products_and_shops_list.service;

import org.springframework.stereotype.Service;
import ru.krutikov.products_and_shops_list.entity.Role;

import java.util.List;

@Service
public interface RoleService {
    List<Role> findAll();
}
