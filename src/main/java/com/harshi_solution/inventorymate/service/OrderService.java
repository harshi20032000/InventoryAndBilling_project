package com.harshi_solution.inventorymate.service;

import java.util.List;

import com.harshi_solution.inventorymate.entities.Order;

public interface OrderService {

	public Order saveOrder(Order order);
	
	public Order getOrderById(Long id);

	public List<Order> getAllOrders();
	
	public Order updateOrderBiltyNo(Long orderId, Long transportId, String biltyNumber);

}
