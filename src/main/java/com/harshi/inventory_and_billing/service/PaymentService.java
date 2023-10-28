package com.harshi.inventory_and_billing.service;

import java.math.BigDecimal;

import com.harshi.inventory_and_billing.entities.Payment;

public interface PaymentService {

	public BigDecimal getTotalOutstanding();
	
	public Payment savePayment(Payment payment);
	
    public Payment getPaymentById(Long id);
    
    public void deletePayment(Long id);
}
