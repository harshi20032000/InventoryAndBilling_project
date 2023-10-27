package com.harshi.InventoryAndBilling.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harshi.InventoryAndBilling.entities.Order;
import com.harshi.InventoryAndBilling.entities.Party;
import com.harshi.InventoryAndBilling.entities.Reps;

public interface OrderRepository extends JpaRepository<Order, Long> {
	
	List<Order> findAllByParty(Party party);
	
	List<Order> findAllByReps(Reps reps);

}
