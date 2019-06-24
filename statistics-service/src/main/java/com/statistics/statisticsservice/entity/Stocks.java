package com.statistics.statisticsservice.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "stocks_info")
public class Stocks {
			
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

		@Column(name = "stockId",unique = true)
		@NotNull
		private String stockId;

	    @Column(name="stock_timestamp")
		@NotNull
	    private Instant timeStamp;

		@Column(name="stock_quantity")
	    private Integer quantity;

		@Column(name="stock_sold")
		private Integer itemSold;

		@Version
		@NotNull
		@Column(name="version")
		private Long version;

		@OneToOne
		@MapsId
		private Product product;

		public Stocks() {}

	public Stocks(Long id, @NotNull String stockId, @NotNull Instant timeStamp, Integer quantity, Integer itemSold,
			@NotNull Long version, Product product) {
		this.id = id;
		this.stockId = stockId;
		this.timeStamp = timeStamp;
		this.quantity = quantity;
		this.itemSold = itemSold;
		this.version = version;
		this.product = product;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStockId() {
		return stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	public Instant getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Instant timeStamp) {
		this.timeStamp = timeStamp;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getItemSold() {
		return itemSold;
	}

	public void setItemSold(Integer itemSold) {
		this.itemSold = itemSold;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Stocks))
			return false;
		Stocks stocks = (Stocks) o;
		return Objects.equals(id, stocks.id) && Objects.equals(stockId, stocks.stockId) && Objects
				.equals(timeStamp, stocks.timeStamp) && Objects.equals(quantity, stocks.quantity) && Objects
				.equals(itemSold, stocks.itemSold) && Objects.equals(version, stocks.version) && Objects
				.equals(product, stocks.product);
	}

	@Override public int hashCode() {
		return Objects.hash(id, stockId, timeStamp, quantity, itemSold, version, product);
	}

	@Override public String toString() {
		final StringBuilder sb = new StringBuilder("Stocks{");
		sb.append("id=").append(id);
		sb.append(", stockId='").append(stockId).append('\'');
		sb.append(", timeStamp=").append(timeStamp);
		sb.append(", quantity=").append(quantity);
		sb.append(", itemSold=").append(itemSold);
		sb.append(", version=").append(version);
		sb.append('}');
		return sb.toString();
	}
}
