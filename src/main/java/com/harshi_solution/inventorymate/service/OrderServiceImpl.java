package com.harshi_solution.inventorymate.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harshi_solution.inventorymate.entities.Order;
import com.harshi_solution.inventorymate.entities.Transport;
import com.harshi_solution.inventorymate.entities.TransportAndBuiltNumber;
import com.harshi_solution.inventorymate.repo.OrderRepository;
import com.harshi_solution.inventorymate.repo.TransportAndBuiltNumberRepository;
import com.harshi_solution.inventorymate.repo.TransportRepository;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private TransportRepository transportRepository;

	@Autowired
	private TransportAndBuiltNumberRepository transportAndBuiltNumberRepository;

	@Autowired
	private OrderStatusHistoryService orderStatusHistoryService;

	@Override
	public Order saveOrder(Order order) {
		return orderRepository.save(order);
	}

	@Override
	public Order getOrderById(Long id) {
		return orderRepository.findById(id).orElse(null);
	}

	@Override
	public List<Order> getAllOrders() {
		return orderRepository.findAll();
	}

	/**
	 * Updates the Builty Number and associated Transport for a given order.
	 *
	 * @param orderId     The ID of the order to be updated.
	 * @param transportId The ID of the new Transport to be associated with the
	 *                    order.
	 * @param biltyNumber The new Builty Number for the order.
	 * @return The updated order with the new Transport and Builty Number.
	 */
	@Override
	public Order updateOrderBiltyNo(Long orderId, Long transportId, String biltyNumber) {
		// Find the order to be updated by its ID
		Order orderToBeUpdated = orderRepository.findById(orderId).orElse(null);

		// Set the orderStatus to "Order Placed"
		// Update the ordrStatus in the received order
		orderStatusHistoryService.addStatusChangeToOrder(orderToBeUpdated, "BuiltyNo Updated");

		// Find the new Transport by its ID
		Transport transportToBeAdded = transportRepository.findById(transportId).orElse(null);

		// Update the Transport and BuiltNumber for the order
		updateTransportAndBuiltNumber(orderToBeUpdated, transportToBeAdded, biltyNumber);

		// Save and return the updated order
		return orderRepository.save(orderToBeUpdated);
	}

	/**
	 * Updates the Transport and Builty Number for the given order. If the order
	 * does not have a TransportAndBuiltNumber, it creates one.
	 *
	 * @param order       The order to be updated.
	 * @param transport   The new Transport to be associated with the order.
	 * @param biltyNumber The new Builty Number for the order.
	 */
	private void updateTransportAndBuiltNumber(Order order, Transport transport, String biltyNumber) {
		TransportAndBuiltNumber transportAndBuiltNumber = order.getTransportAndBuiltNumber();

		if (transportAndBuiltNumber == null) {
			// If no TransportAndBuiltNumber exists, create a new one
			transportAndBuiltNumber = new TransportAndBuiltNumber();
			transportAndBuiltNumber.setTransport(transport);
			transportAndBuiltNumber.setBuiltNumber(biltyNumber);
			transportAndBuiltNumber = transportAndBuiltNumberRepository.save(transportAndBuiltNumber);

		}

		// Set the updated Transport and Builty Number
		transportAndBuiltNumber.setTransport(transport);
		transportAndBuiltNumber.setBuiltNumber(biltyNumber);

		// Associate it with the order
		order.setTransportAndBuiltNumber(transportAndBuiltNumber);
	}

}
