package com.stock.service.stockservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class ProductDTO {

    @NotNull
    @JsonProperty("productId")
    private String productId;

    @NotNull
    @JsonProperty("requestTimestamp")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private String requestTimestamp;

    @NotNull
    @JsonProperty("stock")
    private StocksDTO stock;

    public ProductDTO(){}

    public ProductDTO(@NotNull String productId, @NotNull String requestTimestamp, @NotNull StocksDTO stock) {
        this.productId = productId;
        this.requestTimestamp = requestTimestamp;
        this.stock = stock;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getRequestTimestamp() {
        return requestTimestamp;
    }

    public void setRequestTimestamp(String requestTimestamp) {
        this.requestTimestamp = requestTimestamp;
    }

    public StocksDTO getStock() {
        return stock;
    }

    public void setStock(StocksDTO stock) {
        this.stock = stock;
    }

    @Override public String toString() {
        final StringBuilder sb = new StringBuilder("ProductDTO{");
        sb.append("productId='").append(productId).append('\'');
        sb.append(", requestTimestamp=").append(requestTimestamp);
        sb.append(", stock=").append(stock);
        sb.append('}');
        return sb.toString();
    }


}
