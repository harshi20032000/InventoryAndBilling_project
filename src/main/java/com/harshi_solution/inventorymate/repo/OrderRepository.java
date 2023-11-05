package com.harshi_solution.inventorymate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harshi_solution.inventorymate.entities.Order;
import com.harshi_solution.inventorymate.entities.Party;
import com.harshi_solution.inventorymate.entities.Reps;

public interface OrderRepository extends JpaRepository<Order, Long> {
	
	List<Order> findAllByParty(Party party);
	
	List<Order> findAllByReps(Reps reps);

}
