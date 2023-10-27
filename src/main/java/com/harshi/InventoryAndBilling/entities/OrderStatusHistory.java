package com.harshi.InventoryAndBilling.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class OrderStatusHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    
    private String orderStatus;
    
    private LocalDateTime updateTimestamp;

	public OrderStatusHistory(String orderStatus2, LocalDateTime updateTimestamp2) {
		this.orderStatus = orderStatus2;
		this.updateTimestamp = updateTimestamp2;
	}
	

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

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public LocalDateTime getUpdateTimestamp() {
		return updateTimestamp;
	}

	public void setUpdateTimestamp(LocalDateTime updateTimestamp) {
		this.updateTimestamp = updateTimestamp;
	}

	@Override
	public String toString() {
		return "OrderStatusHistory [id=" + id + ", order=" + order + ", orderStatus=" + orderStatus
				+ ", updateTimestamp=" + updateTimestamp + "]";
	}

	public OrderStatusHistory(Long id, Order order, String orderStatus, LocalDateTime updateTimestamp) {
		super();
		this.id = id;
		this.order = order;
		this.orderStatus = orderStatus;
		this.updateTimestamp = updateTimestamp;
	}

	public OrderStatusHistory() {
		super();
	}
    
}

