package com.harshi.InventoryAndBilling.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harshi.InventoryAndBilling.repo.PaymentRepository;

@Service
public class PaymentServiceImpl implements PaymentService {

	 @Autowired
	 private PaymentRepository paymentRepository;
	 
	 public Long getTotalOutstanding() {
	        // Use paymentRepository to fetch the sum of payAmount for payments with payType 'due'
	        return paymentRepository.sumPayAmountByPayType("due");
	    }

}
