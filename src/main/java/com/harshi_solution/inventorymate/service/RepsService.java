package com.harshi_solution.inventorymate.service;

import java.util.List;

import com.harshi_solution.inventorymate.entities.Order;
import com.harshi_solution.inventorymate.entities.Reps;

public interface RepsService {
	
    List<Reps> getRepsList();

    Reps saveReps(Reps reps);

    Reps getRepsById(Long id);
    
    public List<Order> getOrderListByReps(Long repId);
}
