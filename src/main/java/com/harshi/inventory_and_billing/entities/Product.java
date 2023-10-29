package com.harshi.inventory_and_billing.entities;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productId;

	@Column(unique = true)
	@Length(min=3, max=30, message="brandName should be min 3 and max 12")
    @NotBlank(message="brandName is mandatory")
	private String brandName;
	
	
	private String pType;

	private BigDecimal basePrice;

	@ElementCollection
	@CollectionTable(name = "product_warehouse_quantity", joinColumns = @JoinColumn(name = "product_id"))
	@MapKeyJoinColumn(name = "warehouse_id")
	@Column(name = "quantity")
	private Map<Warehouse, Integer> warehouseQuantities = new HashMap<>();

	// Constructors, getters, and setters

	public Product() {
		// Default constructor
	}

	public Product(String brandName, String pType) {
		this.brandName = brandName;
		this.pType = pType;
	}

	// Getters and setters

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getPType() {
		return pType;
	}

	public void setPType(String pType) {
		this.pType = pType;
	}

	// Other methods if needed

	public BigDecimal getPrice() {
		return basePrice;
	}

	public void setPrice(BigDecimal price) {
		this.basePrice = price;
	}

	public Map<Warehouse, Integer> getWarehouseQuantities() {
		return warehouseQuantities;
	}

	public void setWarehouseQuantities(Map<Warehouse, Integer> warehouseQuantities) {
		this.warehouseQuantities = warehouseQuantities;
	}

	@Override
	public String toString() {
		return "Product{" + "productId=" + productId + ", brandName='" + brandName + '\'' + ", basePrice='" + basePrice
				+ '\'' + ", pType='" + pType + '\'' + '}';
	}
}
