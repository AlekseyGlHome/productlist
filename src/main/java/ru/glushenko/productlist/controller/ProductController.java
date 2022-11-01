package ru.glushenko.productlist.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.glushenko.productlist.model.dto.ProductDto;
import ru.glushenko.productlist.service.ProductService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Api(value = "ProductController" , tags = {"Product Controller"})
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @ApiOperation(value = "List of all products", response = ArrayList.class, tags = "Product Controller")
    @GetMapping()
    public ResponseEntity<List<ProductDto>> getAllProduct() {

        return ResponseEntity.ok(productService.findAll());
    }

    @ApiOperation(value = "Get product by id ", response = ProductDto.class, tags = "Product Controller")
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(
            @ApiParam("Id for product search") @PathVariable long id) {

        return ResponseEntity.ok(productService.findById(id));
    }

    @ApiOperation( value = "Add new product", tags = "Product Controller")
    @PostMapping("/new")
    public ResponseEntity<ProductDto> addProduct(@Valid @RequestBody ProductDto productDto) {

        return ResponseEntity.ok(productService.add(productDto));
    }
}


