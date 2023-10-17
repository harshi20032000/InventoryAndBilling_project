package com.harshi.InventoryAndBilling.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harshi.InventoryAndBilling.entities.Reps;
import com.harshi.InventoryAndBilling.repo.RepsRepository;

@Service
public class RepsServiceImpl implements RepsService {

    @Autowired
    private RepsRepository repsRepository;

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
}

