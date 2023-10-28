package com.harshi.inventory_and_billing.service;

import java.util.List;

import com.harshi.inventory_and_billing.entities.Order;

public interface OrderService {

	public Order saveOrder(Order order);
	
	public Order getOrderById(Long id);

	public List<Order> getAllOrders();
	
	public Order updateOrderBiltyNo(Long orderId, Long transportId, String biltyNumber);

}
