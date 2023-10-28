package com.harshi.inventory_and_billing.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harshi.inventory_and_billing.entities.Reps;

@Repository
public interface RepsRepository extends JpaRepository<Reps, Long> {
    
	
}

