package com.harshi.inventory_and_billing.entities;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * An OrderLineItem represents an individual line item within an order. It
 * contains information about a specific product, the quantity of that product
 * ordered, and the rate at which it is offered in the order.
 */

@Entity
@Table(name = "order_line_items")
public class OrderLineItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	@NotNull
	private int quantity;

	@NotNull
	private BigDecimal rate;

	@ElementCollection
	@CollectionTable(name = "order_line_item_warehouse_quantities", joinColumns = @JoinColumn(name = "order_line_item_id"))
	@MapKeyJoinColumn(name = "warehouse_id")
	@Column(name = "quantity")
	private Map<Warehouse, Integer> orderWarehouseQuantities ;

	// Constructors, getters, and setters

	public OrderLineItem() {
		// Default constructor
	}

	public OrderLineItem(Order order, Product product, int quantity, BigDecimal rate) {
		this.order = order;
		this.product = product;
		this.quantity = quantity;
		this.rate = rate;
	}

	// Getters and setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	// Other methods if needed

	public Map<Warehouse, Integer> getOrderWarehouseQuantities() {
		return orderWarehouseQuantities;
	}

	public void setOrderWarehouseQuantities(Map<Warehouse, Integer> orderWarehouseQuantities) {
		this.orderWarehouseQuantities = orderWarehouseQuantities;
	}

	@Override
	public String toString() {
		return "OrderLineItem{" + "id=" + id + ", order=" + order + ", product=" + product + ", quantity=" + quantity
				+ ", rate=" + rate + '}';
	}
}
