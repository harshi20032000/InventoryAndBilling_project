package com.harshi.InventoryAndBilling.service;

import com.harshi.InventoryAndBilling.entities.Order;
import com.harshi.InventoryAndBilling.entities.OrderStatus;
import com.harshi.InventoryAndBilling.entities.OrderStatusHistory;

public interface OrderStatusHistoryService {

	OrderStatusHistory saveOrderStatusHistory(OrderStatusHistory statusHistory);

	void addStatusChangeToOrder(Order order, String orderStatus);
}
