package com.harshi_solution.inventorymate.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harshi_solution.inventorymate.entities.Transport;

public interface TransportRepository extends JpaRepository<Transport, Long> {

}
