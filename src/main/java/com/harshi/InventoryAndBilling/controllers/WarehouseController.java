package com.harshi.InventoryAndBilling.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.harshi.InventoryAndBilling.entities.Product;
import com.harshi.InventoryAndBilling.entities.Warehouse;
import com.harshi.InventoryAndBilling.service.WarehouseService;
@Controller
public class WarehouseController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WarehouseController.class);


	@Autowired
	private WarehouseService warehouseService;
	
	/**
	 * This ia an empty product attribute so that the form can us this while
	 * rendering
	 */
	@ModelAttribute("warehouse")
	public Warehouse getWarehouse() {
		return new Warehouse();
	}
	
	@RequestMapping("/showWarehouseList")
	public String showWarehouseList(ModelMap modelMap) {
		LOGGER.info("Inside showWarehouseList() on WarehouseController");
		// Retrieve all warehouses and add them to the modelMap
		List<Warehouse> warehousesList = warehouseService.showWarehousesList();
		modelMap.addAttribute("productsList", warehousesList);
		return "dashboard/warehousesList";
	}

	@RequestMapping("/showAddWarehouse")
	public String showAddWarehouse() {
		LOGGER.info("Inside showAddWarehouse() on WarehouseController");
		return "dashboard/addWarehouse";
	}

	@RequestMapping("/addWarehouse")
	public String addWarehouse(@ModelAttribute("warehouse") Warehouse warehouse, ModelMap modelMap) {
		Warehouse savedWarehouse = warehouseService.saveWarehouse(warehouse);
		List<Warehouse> warehousesList = warehouseService.showWarehousesList();
		modelMap.addAttribute("warehousesList", warehousesList);
		String msg = new String("Warehouse saved with id - " + savedWarehouse.getWareId());
		modelMap.addAttribute("msg", msg);
		// emailUtil.sendEmail("learningpandit@gmail.com", msg,
		// savedProduct.toString());
		return "dashboard/warehousesList";
	}
}
