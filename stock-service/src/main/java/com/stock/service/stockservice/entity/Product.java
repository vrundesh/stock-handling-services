package com.stock.service.stockservice.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table (name = "products_info")
public class Product {
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

	 @Column(name = "product_name",unique = true)
	 @NotNull
     private String productName;

	 @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
	 private Stocks stock;

	 public Product() {}

	public Product(Long id, @NotNull String productName, Stocks stock, @NotNull Long version) {
		this.id = id;
		this.productName = productName;
		this.stock = stock;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Stocks getStock() {
		return stock;
	}

	public void setStock(Stocks stock) {
		this.stock = stock;
	}

	@Override public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Product))
			return false;
		Product product = (Product) o;
		return Objects.equals(id, product.id) && Objects.equals(productName, product.productName) && Objects
				.equals(stock, product.stock) ;
	}

	@Override public int hashCode() {
		return Objects.hash(id, productName, stock);
	}

	@Override public String toString() {
		final StringBuilder sb = new StringBuilder("Product{");
		sb.append("id=").append(id);
		sb.append(", productName='").append(productName).append('\'');
		sb.append(", stock=").append(stock);
		sb.append('}');
		return sb.toString();
	}
}
