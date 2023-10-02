package com.harshi.InventoryAndBilling.entities;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MapKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productId;

	private String brandName;
	private String pType;

	private BigDecimal basePrice;

	@ManyToMany(mappedBy = "products")
	private Set<Warehouse> warehouses = new HashSet<>();

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

	public Set<Warehouse> getWarehouses() {
		return warehouses;
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
