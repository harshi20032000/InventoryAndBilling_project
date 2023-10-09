package com.harshi.InventoryAndBilling.controllers;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.harshi.InventoryAndBilling.entities.Order;
import com.harshi.InventoryAndBilling.entities.Party;
import com.harshi.InventoryAndBilling.entities.Reps;
import com.harshi.InventoryAndBilling.service.OrderService;
import com.harshi.InventoryAndBilling.service.PartyService;
import com.harshi.InventoryAndBilling.service.ProductService;
import com.harshi.InventoryAndBilling.service.RepsService;
import com.harshi.InventoryAndBilling.service.WarehouseService;
@Controller
public class OrderController {
	
	@Autowired
	private ProductService productService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	private WarehouseService warehouseService;
	
	@Autowired
    private RepsService repsService;
	
	@Autowired
    private OrderService orderService;
	
	@Autowired
	private PartyService partyService;
	
	@GetMapping("/showBookOrder")
	public String showBookOrder(ModelMap modelMap) {
	    // 1. Get the current date
	    LocalDate currentDate = LocalDate.now();
	    modelMap.addAttribute("currentDate", currentDate);

	    // 2. Get the list of representatives from repsService
	    List<Reps> repsList = repsService.showRepsList();
	    modelMap.addAttribute("repsList", repsList);

	    // Return the view name where users can select a representative
	    return "OrderView/bookOrderSelectReps";
	}
	
	@PostMapping("/bookOrderSaveReps")
	public String bookOrderSaveReps(@RequestParam("selectedRep") Long selectedRepId, @RequestParam("currentDate") LocalDate currentDate, ModelMap modelMap) {
	    // Create a new Order object
	    Order order = new Order();
	    order.setOrderDate(Date.valueOf(currentDate));

	    // Get the selected representative by ID
	    Reps selectedRep = repsService.getRepsById(selectedRepId);
	    order.setReps(selectedRep);

	    // Save the order to the database
	    Order savedOrder = orderService.saveOrder(order);
	    List<Party> partyList = partyService.getPartyList();
	    modelMap.addAttribute("order",  savedOrder);
	    modelMap.addAttribute("partyList",  partyList);

	    // Redirect to the next step with the order ID
	    return "OrderView/bookOrderSelectParty";
	}
	
	@PostMapping("/bookOrderSaveParty")
	public String bookOrderSaveParty(@RequestParam("selectedParty") Long selectedPartyId, @RequestParam("orderId") Long orderId, ModelMap modelMap) {
	    // Get the order by ID
	    Order order = orderService.getOrderById(orderId);

	    // Get the party by ID
	    Party selectedParty = partyService.getPartyById(selectedPartyId);

	    // Set the received party in the received order
	    order.setParty(selectedParty);

	    // Update the order with the new party
	    Order updatedOrder = orderService.saveOrder(order);

	    modelMap.addAttribute("order", updatedOrder);

	    // Redirect to the next step or view
	    return "OrderView/bookOrderSelectProducts";
	}



}

