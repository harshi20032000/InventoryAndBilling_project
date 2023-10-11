package com.harshi.InventoryAndBilling.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harshi.InventoryAndBilling.entities.Transport;

public interface TransportRepository extends JpaRepository<Transport, Long> {

}
