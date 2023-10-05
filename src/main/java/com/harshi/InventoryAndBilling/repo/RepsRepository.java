package com.harshi.InventoryAndBilling.repo;

import com.harshi.InventoryAndBilling.entities.Reps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepsRepository extends JpaRepository<Reps, Long> {
    // Define custom query methods if needed
}

