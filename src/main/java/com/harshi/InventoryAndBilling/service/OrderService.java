package com.harshi.InventoryAndBilling.service;

import java.util.List;

import com.harshi.InventoryAndBilling.entities.Order;

public interface OrderService {

	public Order saveOrder(Order order);
	
	public Order getOrderById(Long id);

	public List<Order> getAllOrders();

}
