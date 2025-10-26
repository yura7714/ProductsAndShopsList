package ru.krutikov.products_and_shops_list.service;

import ru.krutikov.products_and_shops_list.entity.Shop;
import ru.krutikov.products_and_shops_list.entity.User;

import java.util.List;
import java.util.Optional;

public interface ShopService {
    void saveShop(Shop shop);

    List<Shop> findAll();

    List<Shop> findAllByUser(String username);

    Optional<Shop> findById(Long shopId);
}
