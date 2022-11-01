package ru.glushenko.productlist.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

public class AddToListRequest {
    @ApiModelProperty(name = "list_id", required = true, value = "123", notes = "list unique code")
    @JsonProperty("list_id")
    private Long listId;

    @ApiModelProperty(name = "product_id", required = true, value = "123", notes = "list unique code")
    @JsonProperty("product_id")
    private Long productId;

    public AddToListRequest(Long listId, Long productId) {
        this.listId = listId;
        this.productId = productId;
    }

    public AddToListRequest() {
    }

    public Long getListId() {
        return listId;
    }

    public void setListId(Long listId) {
        this.listId = listId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
