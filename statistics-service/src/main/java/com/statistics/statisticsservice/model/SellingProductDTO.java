package com.statistics.statisticsservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class SellingProductDTO {

    @NotNull
    @JsonProperty("productId")
    private String productId;

    @NotNull
    @JsonProperty("itemsSold")
    private Integer itemsSold;

    public SellingProductDTO(){}

    public SellingProductDTO(@NotNull String productId, @NotNull Integer itemsSold) {
        this.productId = productId;
        this.itemsSold = itemsSold;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getItemsSold() {
        return itemsSold;
    }

    public void setItemsSold(Integer itemsSold) {
        this.itemsSold = itemsSold;
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof SellingProductDTO))
            return false;
        SellingProductDTO that = (SellingProductDTO) o;
        return Objects.equals(productId, that.productId) && Objects.equals(itemsSold, that.itemsSold);
    }

    @Override public int hashCode() {
        return Objects.hash(productId, itemsSold);
    }

    @Override public String toString() {
        final StringBuilder sb = new StringBuilder("SellingProductDTO{");
        sb.append("productId='").append(productId).append('\'');
        sb.append(", itemsSold=").append(itemsSold);
        sb.append('}');
        return sb.toString();
    }
}
