package ru.krutikov.products_and_shops_list.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.krutikov.products_and_shops_list.entity.Shop;
import ru.krutikov.products_and_shops_list.repository.ShopRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;

    @Autowired
    public ShopServiceImpl(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    @Override
    public void saveShop(Shop shop) {
        log.info("Сохранение магазина {} пользователем {}", shop.getName(), shop.getCreatedBy().getUsername());
        shopRepository.save(shop);
    }

    @Override
    public List<Shop> findAll() {
        log.info("Получение списка всех магазинов");
        return shopRepository.findAll();
    }

    @Override
    public List<Shop> findAllByUser(String username) {
        log.info("Получение списка магазинов пользователя: {}", username);
        return shopRepository.findByCreatedByUsername(username);
    }

    @Override
    public Optional<Shop> findById(Long shopId) {
        log.info("Поиск магазина по id: {}", shopId);
        return shopRepository.findById(shopId);
    }

    @Override
    public void deleteById(Long shopId) {
        log.info("Удаление магазина по id: {}", shopId);
        shopRepository.deleteById(shopId);
    }
}
