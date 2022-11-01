package ru.glushenko.productlist.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import ru.glushenko.productlist.model.entity.ProductEntity;

import javax.validation.constraints.NotEmpty;
import java.util.Objects;

public class ProductDto {

    @ApiModelProperty(name = "id", value = "123", notes = "product unique code")
    private Long id;

    @ApiModelProperty(name = "name", required = true, value = " Name product", notes = "Name product")
    @NotEmpty(message = "name must not be empty")
    private String name;

    @ApiModelProperty(name = "description", value = "Description product", notes = "Description product")
    private String description;

    @ApiModelProperty(name = "kcal", value = "123", notes = "Calories product")
    private int kcal;

    public ProductDto(Long id, String name, String description, int kcal) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.kcal = kcal;
    }

    public ProductDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getKcal() {
        return kcal;
    }

    public void setKcal(int kcal) {
        this.kcal = kcal;
    }

    public static ProductDto getProductDto(ProductEntity product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setKcal(product.getKcal());
        return productDto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDto that = (ProductDto) o;
        return kcal == that.kcal && Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, kcal);
    }
}