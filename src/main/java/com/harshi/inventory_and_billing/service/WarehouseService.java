package com.harshi.inventory_and_billing.service;

import java.util.List;

import com.harshi.inventory_and_billing.entities.Warehouse;

public interface WarehouseService {

	List<Warehouse> getWarehousesList();

	Warehouse saveWarehouse(Warehouse warehouse);

	Warehouse getWarehouseById(Long warehouseId);
}
