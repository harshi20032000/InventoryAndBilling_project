package com.harshi.InventoryAndBilling.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harshi.InventoryAndBilling.entities.Payment;
import com.harshi.InventoryAndBilling.repo.PaymentRepository;

@Service
public class PaymentServiceImpl implements PaymentService {

	 @Autowired
	 private PaymentRepository paymentRepository;
	 
	 public Long getTotalOutstanding() {
	        return paymentRepository.sumPayAmountByPayType("due");
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
