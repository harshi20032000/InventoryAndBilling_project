package com.harshi_solution.inventorymate.service;

import java.util.List;

import com.harshi_solution.inventorymate.entities.Warehouse;

public interface WarehouseService {

	List<Warehouse> getWarehousesList();

	Warehouse saveWarehouse(Warehouse warehouse);

	Warehouse getWarehouseById(Long warehouseId);
}
