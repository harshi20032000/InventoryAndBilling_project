package com.harshi.InventoryAndBilling.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harshi.InventoryAndBilling.entities.Warehouse;
import com.harshi.InventoryAndBilling.repo.WarehouseRepository;
@Service
public class WarehouseServiceImpl implements WarehouseService {

	@Autowired
	private WarehouseRepository warehouseRepository;

	@Override
	public List<Warehouse> showWarehousesList() {
		return warehouseRepository.findAll();
	}

	@Override
	public Warehouse saveWarehouse(Warehouse warehouse) {
		return warehouseRepository.save(warehouse);
	}

}
