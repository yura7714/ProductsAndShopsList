package ru.krutikov.products_and_shops_list.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.krutikov.products_and_shops_list.entity.Shop;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
}
