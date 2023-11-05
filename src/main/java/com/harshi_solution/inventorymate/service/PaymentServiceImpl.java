package com.harshi_solution.inventorymate.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harshi_solution.inventorymate.entities.Order;
import com.harshi_solution.inventorymate.entities.Payment;
import com.harshi_solution.inventorymate.helper.OrderHelper;
import com.harshi_solution.inventorymate.repo.OrderRepository;
import com.harshi_solution.inventorymate.repo.PaymentRepository;

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
			BigDecimal totalOrderPrice = OrderHelper.totalOrderPrice(eachOrder);
			BigDecimal pendingPrice = OrderHelper.totalPendingPrice(eachOrder, totalOrderPrice);
			totalPendingAmount=totalPendingAmount.add(pendingPrice);
		}
		return totalPendingAmount;
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
