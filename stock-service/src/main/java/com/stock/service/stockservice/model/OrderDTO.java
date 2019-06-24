package com.stock.service.stockservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class OrderDTO {

    @NotNull
    @JsonProperty("productId")
    private String productId;

    @NotNull
    @JsonProperty("itemSold")
    private Integer itemSold;

    public OrderDTO(){  }

    public OrderDTO(@NotNull String productId, @NotNull Integer itemSold) {
        this.productId = productId;
        this.itemSold = itemSold;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getItemSold() {
        return itemSold;
    }

    public void setItemSold(Integer itemSold) {
        this.itemSold = itemSold;
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof OrderDTO))
            return false;
        OrderDTO orderDTO = (OrderDTO) o;
        return Objects.equals(productId, orderDTO.productId) && Objects.equals(itemSold, orderDTO.itemSold);
    }

    @Override public int hashCode() {
        return Objects.hash(productId, itemSold);
    }

    @Override public String toString() {
        final StringBuilder sb = new StringBuilder("OrderDTO{");
        sb.append("productId='").append(productId).append('\'');
        sb.append(", itemSold=").append(itemSold);
        sb.append('}');
        return sb.toString();
    }
}
