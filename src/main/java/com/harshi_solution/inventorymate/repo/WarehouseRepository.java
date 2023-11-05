package com.harshi_solution.inventorymate.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harshi_solution.inventorymate.entities.Warehouse;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

}
