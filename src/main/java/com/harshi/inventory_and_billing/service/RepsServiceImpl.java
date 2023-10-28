package com.harshi.inventory_and_billing.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harshi.inventory_and_billing.entities.Order;
import com.harshi.inventory_and_billing.entities.Reps;
import com.harshi.inventory_and_billing.repo.OrderRepository;
import com.harshi.inventory_and_billing.repo.RepsRepository;

@Service
public class RepsServiceImpl implements RepsService {

    @Autowired
    private RepsRepository repsRepository;
    
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Reps> getRepsList() {
        return repsRepository.findAll();
    }

    @Override
    public Reps saveReps(Reps reps) {
        return repsRepository.save(reps);
    }

    @Override
    public Reps getRepsById(Long id) {
        return repsRepository.findById(id).orElse(null);
    }

	@Override
	public List<Order> getOrderListByReps(Long repId) {
		Reps reps = repsRepository.findById(repId).orElse(null);
		return orderRepository.findAllByReps(reps);
	}
}

