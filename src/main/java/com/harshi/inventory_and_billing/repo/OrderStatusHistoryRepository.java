package com.harshi.inventory_and_billing.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harshi.inventory_and_billing.entities.OrderStatusHistory;

@Repository
public interface OrderStatusHistoryRepository extends JpaRepository<OrderStatusHistory, Long> {
    // You can add custom query methods here if needed
}
