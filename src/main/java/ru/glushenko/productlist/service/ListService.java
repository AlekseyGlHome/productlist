package ru.glushenko.productlist.service;

import ru.glushenko.productlist.model.dto.ListDto;

import java.util.List;

public interface ListService {

    ListDto findById(long id);

    List<ListDto> findAll();

    ListDto add(ListDto listDto);

    ListDto addProductToLit(long listId, long productId);
}
