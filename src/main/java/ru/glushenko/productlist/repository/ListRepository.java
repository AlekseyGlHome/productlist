package ru.glushenko.productlist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.glushenko.productlist.model.entity.ListEntity;

public interface ListRepository extends JpaRepository<ListEntity, Long> {
}