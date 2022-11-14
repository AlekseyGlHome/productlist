package ru.glushenko.productlist.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import ru.glushenko.productlist.model.entity.ProductEntity;

import javax.validation.constraints.NotEmpty;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
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

    public static ProductDto getProductDto(ProductEntity product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setKcal(product.getKcal());
        return productDto;
    }
}