package ru.krutikov.products_and_shops_list.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.krutikov.products_and_shops_list.entity.Shop;
import ru.krutikov.products_and_shops_list.entity.User;
import ru.krutikov.products_and_shops_list.repository.ShopRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;

    @Autowired
    public ShopServiceImpl(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    @Override
    public void saveShop(Shop shop) {
        shopRepository.save(shop);
    }

    @Override
    public List<Shop> findAll() {
        return shopRepository.findAll();
    }

    @Override
    public List<Shop> findAllByUser(String username) {
        return shopRepository.findByCreatedByUsername(username);
    }

    @Override
    public Optional<Shop> findById(Long shopId) {
        return shopRepository.findById(shopId);
    }
}
