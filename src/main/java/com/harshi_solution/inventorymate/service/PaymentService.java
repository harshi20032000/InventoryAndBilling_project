package com.harshi_solution.inventorymate.service;

import java.math.BigDecimal;

import com.harshi_solution.inventorymate.entities.Payment;

public interface PaymentService {

	public BigDecimal getTotalOutstanding();
	
	public Payment savePayment(Payment payment);
	
    public Payment getPaymentById(Long id);
    
    public void deletePayment(Long id);
}
