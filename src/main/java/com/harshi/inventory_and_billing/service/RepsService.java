package com.harshi.inventory_and_billing.service;

import java.util.List;

import com.harshi.inventory_and_billing.entities.Order;
import com.harshi.inventory_and_billing.entities.Reps;

public interface RepsService {
	
    List<Reps> getRepsList();

    Reps saveReps(Reps reps);

    Reps getRepsById(Long id);
    
    public List<Order> getOrderListByReps(Long repId);
}
