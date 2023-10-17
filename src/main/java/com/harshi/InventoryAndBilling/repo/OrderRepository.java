package com.harshi.InventoryAndBilling.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harshi.InventoryAndBilling.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
