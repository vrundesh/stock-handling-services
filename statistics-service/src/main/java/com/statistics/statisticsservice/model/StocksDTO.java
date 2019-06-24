package com.statistics.statisticsservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class StocksDTO {
	
	@NotNull(message = "Stock Id Cannot Be Empty")
    @JsonProperty(value = "id", required = true)
    private String stockid;

    @JsonProperty(value = "timestamp",required = false)
	private String timeStamp;
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(value = "productId",required = true)
	@NotNull(message = "Product Id Cannot Be Empty")
    private String productId;

    @NotNull(message = "Stocks Id Cannot Be Empty")
    @JsonProperty(value = "quantity",required = true)
    private Integer quantity;

	@JsonIgnore
	private Long version;

    public StocksDTO() {   	
    }

	public StocksDTO(@NotNull(message = "Stock Id Cannot Be Empty") String stockid, String timeStamp,
			@NotNull(message = "Product Id Cannot Be Empty") String productId,
			@NotNull(message = "Stocks Id Cannot Be Empty") Integer quantity) {
		this.stockid = stockid;
		this.timeStamp = timeStamp;
		this.productId = productId;
		this.quantity = quantity;
	}

	public StocksDTO(@NotNull(message = "Stock Id Cannot Be Empty") String stockid, String timeStamp,
			@NotNull(message = "Product Id Cannot Be Empty") String productId,
			@NotNull(message = "Stocks Id Cannot Be Empty") Integer quantity, Long version) {
		this.stockid = stockid;
		this.timeStamp = timeStamp;
		this.productId = productId;
		this.quantity = quantity;
		this.version = version;
	}

	public String getStockid() {
		return stockid;
	}

	public void setStockid(String stockid) {
		this.stockid = stockid;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	@Override public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof StocksDTO))
			return false;
		StocksDTO stocksDTO = (StocksDTO) o;
		return Objects.equals(stockid, stocksDTO.stockid) && Objects.equals(timeStamp, stocksDTO.timeStamp) && Objects
				.equals(productId, stocksDTO.productId) && Objects.equals(quantity, stocksDTO.quantity) && Objects
				.equals(version, stocksDTO.version);
	}

	@Override public int hashCode() {
		return Objects.hash(stockid, timeStamp, productId, quantity, version);
	}

	@Override public String toString() {
		final StringBuilder sb = new StringBuilder("StocksDTO{");
		sb.append("stockid='").append(stockid).append('\'');
		sb.append(", timeStamp='").append(timeStamp).append('\'');
		sb.append(", productId='").append(productId).append('\'');
		sb.append(", quantity=").append(quantity);
		sb.append(", version=").append(version);
		sb.append('}');
		return sb.toString();
	}
}
