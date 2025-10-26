package ru.krutikov.products_and_shops_list.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.krutikov.products_and_shops_list.entity.Shop;
import ru.krutikov.products_and_shops_list.entity.User;

import java.util.List;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
    List<Shop> findByCreatedByUsername(String username);
}
