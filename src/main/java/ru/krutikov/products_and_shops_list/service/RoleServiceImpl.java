package ru.krutikov.products_and_shops_list.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.krutikov.products_and_shops_list.entity.Role;
import ru.krutikov.products_and_shops_list.repository.RoleRepository;

import java.util.List;

@Slf4j
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> findAll() {
        log.info("Получение списка всех ролей");
        return roleRepository.findAll();
    }
}
