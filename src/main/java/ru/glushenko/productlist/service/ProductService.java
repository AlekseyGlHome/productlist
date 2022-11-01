package ru.glushenko.productlist.service;

import ru.glushenko.productlist.model.dto.ProductDto;

import java.util.List;

public interface ProductService {

    ProductDto findById(long id);

    List<ProductDto> findAll();

    ProductDto add(ProductDto productDto);
}
