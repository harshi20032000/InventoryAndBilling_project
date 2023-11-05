package com.harshi_solution.inventorymate.service;

import com.harshi_solution.inventorymate.entities.Order;
import com.harshi_solution.inventorymate.entities.OrderStatusHistory;

public interface OrderStatusHistoryService {

	OrderStatusHistory saveOrderStatusHistory(OrderStatusHistory statusHistory);

	void addStatusChangeToOrder(Order order, String orderStatus);
}
