package com.harshi_solution.inventorymate.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harshi_solution.inventorymate.entities.Warehouse;
import com.harshi_solution.inventorymate.repo.WarehouseRepository;
@Service
public class WarehouseServiceImpl implements WarehouseService {

	@Autowired
	private WarehouseRepository warehouseRepository;

	@Override
	public List<Warehouse> getWarehousesList() {
		return warehouseRepository.findAll();
	}

	@Override
	public Warehouse saveWarehouse(Warehouse warehouse) {
		warehouse.setWareName(warehouse.getWareName().toUpperCase());
		warehouse.setWareCode(warehouse.getWareCode().toUpperCase());
		return warehouseRepository.save(warehouse);
	}

	@Override
	public Warehouse getWarehouseById(Long warehouseId) {
		return warehouseRepository.findById(warehouseId).get();
	}

}
