package com.harshi.InventoryAndBilling.service;

import java.math.BigDecimal;

import com.harshi.InventoryAndBilling.entities.Payment;

public interface PaymentService {

	public BigDecimal getTotalOutstanding();
	
	public Payment savePayment(Payment payment);
	
    public Payment getPaymentById(Long id);
    
    public void deletePayment(Long id);
}
