package ru.glushenko.productlist.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddToListRequest {
    @ApiModelProperty(name = "list_id", required = true, value = "123", notes = "list unique code")
    @JsonProperty("list_id")
    private Long listId;

    @ApiModelProperty(name = "product_id", required = true, value = "123", notes = "list unique code")
    @JsonProperty("product_id")
    private Long productId;
}
