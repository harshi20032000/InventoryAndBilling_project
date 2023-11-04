package com.harshi_solution.inventorymate.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harshi_solution.inventorymate.entities.Reps;

@Repository
public interface RepsRepository extends JpaRepository<Reps, Long> {
    
	
}

