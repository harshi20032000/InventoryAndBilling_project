package com.harshi.InventoryAndBilling.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harshi.InventoryAndBilling.entities.Order;
import com.harshi.InventoryAndBilling.entities.OrderLineItem;
import com.harshi.InventoryAndBilling.entities.Payment;
import com.harshi.InventoryAndBilling.repo.OrderRepository;
import com.harshi.InventoryAndBilling.repo.PaymentRepository;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private OrderRepository orderRepository;

	public BigDecimal getTotalOutstanding() {
		List<Order> orderList = orderRepository.findAll();
		BigDecimal totalPendingAmount= new BigDecimal(0);
		for(Order eachOrder : orderList) {
			BigDecimal totalOrderPrice = totalOrderPrice(eachOrder);
			BigDecimal pendingPrice = totalPendingPrice(eachOrder, totalOrderPrice);
			totalPendingAmount=totalPendingAmount.add(pendingPrice);
		}
		return totalPendingAmount;
	}
	
	private BigDecimal totalOrderPrice(Order order) {
		BigDecimal totalPrice = new BigDecimal(0);
		for (OrderLineItem eachOrderLineItem : order.getOrderLineItems()) {
			totalPrice = totalPrice
					.add(eachOrderLineItem.getRate().multiply(new BigDecimal(eachOrderLineItem.getQuantity())));
		}
		return totalPrice;
	}

	private BigDecimal totalPendingPrice(Order order, BigDecimal totalAmount) {
		BigDecimal remainingPrice = totalAmount;
		for (Payment eachPayment : order.getPayments()) {
			remainingPrice = remainingPrice.subtract(new BigDecimal(eachPayment.getPayAmount()));
		}
		return remainingPrice;
	}

	@Override
	public Payment savePayment(Payment payment) {
		return paymentRepository.save(payment);
	}

	@Override
	public Payment getPaymentById(Long id) {
		return paymentRepository.findById(id).orElse(null);
	}

	@Override
	public void deletePayment(Long id) {
		paymentRepository.deleteById(id);
	}

}
