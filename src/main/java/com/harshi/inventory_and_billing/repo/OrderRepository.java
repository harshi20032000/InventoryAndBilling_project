package com.harshi.inventory_and_billing.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harshi.inventory_and_billing.entities.Order;
import com.harshi.inventory_and_billing.entities.Party;
import com.harshi.inventory_and_billing.entities.Reps;

public interface OrderRepository extends JpaRepository<Order, Long> {
	
	List<Order> findAllByParty(Party party);
	
	List<Order> findAllByReps(Reps reps);

}
