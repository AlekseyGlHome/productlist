package ru.glushenko.productlist.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import ru.glushenko.productlist.model.entity.ListEntity;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ListDto {

    @ApiModelProperty(name = "id",required = true, value = "123", notes = "list unique code")
    private Long id;

    @ApiModelProperty(name = "name",required = true, value = " Name product", notes = "Name product" )
    @NotEmpty(message = "name must not be empty")
    private String name;

    @ApiModelProperty(name = "amount_kcal", value = " 123", notes = "Product calories" )
    @JsonProperty("amount_kcal")
    private int amountKcal;

    @ApiModelProperty(name = "products", value = "Collection product", notes = "Collection product" )
    private List<ProductDto> products = new ArrayList<>();

    public ListDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public ListDto() {
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

    public int getAmountKcal() {
        setAmountKcal(products.stream().mapToInt(ProductDto::getKcal).sum());
        return amountKcal;
    }

    public void setAmountKcal(int amountKcal) {
        this.amountKcal = amountKcal;
    }

    public List<ProductDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDto> products) {
        this.products = products;
    }


    public static ListDto getListDto(ListEntity listEntity) {
        ListDto listDto = new ListDto();
        listDto.setId(listEntity.getId());
        listDto.setName(listEntity.getName());
        List<ProductDto> productDtoList = listEntity.getProducts().stream()
                .map(ProductDto::getProductDto)
                .collect(Collectors.toList());
        listDto.setProducts(productDtoList);


        return listDto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListDto listDto = (ListDto) o;
        return Objects.equals(id, listDto.id) && Objects.equals(name, listDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}