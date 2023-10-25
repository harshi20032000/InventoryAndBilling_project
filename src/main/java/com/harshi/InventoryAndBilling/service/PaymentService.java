package com.harshi.InventoryAndBilling.service;

import com.harshi.InventoryAndBilling.entities.Payment;

public interface PaymentService {

	public Long getTotalOutstanding();
	
	public Payment savePayment(Payment payment);
	
    public Payment getPaymentById(Long id);
    
    public void deletePayment(Long id);
}
