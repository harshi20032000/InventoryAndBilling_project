package com.harshi.inventory_and_billing.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harshi.inventory_and_billing.entities.Order;
import com.harshi.inventory_and_billing.entities.OrderStatusHistory;
import com.harshi.inventory_and_billing.repo.OrderStatusHistoryRepository;

@Service
public class OrderStatusHistoryServiceImpl implements OrderStatusHistoryService {

	private final OrderStatusHistoryRepository statusHistoryRepository;

	@Autowired
	public OrderStatusHistoryServiceImpl(OrderStatusHistoryRepository statusHistoryRepository) {
		this.statusHistoryRepository = statusHistoryRepository;
	}

	@Override
	public OrderStatusHistory saveOrderStatusHistory(OrderStatusHistory statusHistory) {
		return statusHistoryRepository.save(statusHistory);
	}

	@Override
	public void addStatusChangeToOrder(Order order, String orderStatus) {
		LocalDateTime updateTimestamp = LocalDateTime.now();
		OrderStatusHistory change = new OrderStatusHistory(orderStatus, updateTimestamp);
		change.setOrder(order);
		OrderStatusHistory savedStatusChange = statusHistoryRepository.save(change);
		order.addStatusChange(savedStatusChange);

	}
}