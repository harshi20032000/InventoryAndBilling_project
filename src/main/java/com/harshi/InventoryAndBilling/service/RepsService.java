package com.harshi.InventoryAndBilling.service;

import java.util.List;

import com.harshi.InventoryAndBilling.entities.Reps;

public interface RepsService {
	
    List<Reps> getRepsList();

    Reps saveReps(Reps reps);

    Reps getRepsById(Long id);
}
