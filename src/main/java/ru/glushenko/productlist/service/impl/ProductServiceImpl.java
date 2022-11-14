package ru.glushenko.productlist.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.glushenko.productlist.Exception.ElementNotFound;
import ru.glushenko.productlist.model.dto.ProductDto;
import ru.glushenko.productlist.model.entity.ProductEntity;
import ru.glushenko.productlist.repository.ProductRepository;
import ru.glushenko.productlist.service.ProductService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ProductDto findById(long id) {
        Optional<ProductEntity> product = productRepository.findById(id);
        return ProductDto.getProductDto(product.orElseThrow(() -> new ElementNotFound("Product with id= " + id + " not found")));
    }

    @Override
    public List<ProductDto> findAll() {
        return productRepository.findAll().stream().map(ProductDto::getProductDto).collect(Collectors.toList());
    }

    @Override
    public ProductDto add(ProductDto productDto) {
        ProductEntity product = createProduct(productDto);
        productDto.setId(product.getId());
        return productDto;
    }

    private ProductEntity createProduct(ProductDto productDto) {
        ProductEntity product = new ProductEntity();
        product.setDescription(productDto.getDescription());
        product.setName(productDto.getName());
        product.setKcal(productDto.getKcal());
        product = productRepository.save(product);

        return product;
    }
}
