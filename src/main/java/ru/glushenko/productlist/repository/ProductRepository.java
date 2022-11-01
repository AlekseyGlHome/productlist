package ru.glushenko.productlist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.glushenko.productlist.model.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}