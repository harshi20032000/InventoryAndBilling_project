package com.harshi.InventoryAndBilling.service;

import com.harshi.InventoryAndBilling.entities.Order;

public interface OrderService {

	public Order saveOrder(Order order);
	
	public Order getOrderById(Long id);

}
