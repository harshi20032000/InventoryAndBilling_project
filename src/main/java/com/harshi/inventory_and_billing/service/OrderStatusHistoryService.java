package com.harshi.inventory_and_billing.service;

import com.harshi.inventory_and_billing.entities.Order;
import com.harshi.inventory_and_billing.entities.OrderStatusHistory;

public interface OrderStatusHistoryService {

	OrderStatusHistory saveOrderStatusHistory(OrderStatusHistory statusHistory);

	void addStatusChangeToOrder(Order order, String orderStatus);
}
