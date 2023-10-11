package com.harshi.InventoryAndBilling.controllers;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.harshi.InventoryAndBilling.entities.Order;
import com.harshi.InventoryAndBilling.entities.OrderLineItem;
import com.harshi.InventoryAndBilling.entities.Party;
import com.harshi.InventoryAndBilling.entities.Product;
import com.harshi.InventoryAndBilling.entities.Reps;
import com.harshi.InventoryAndBilling.entities.Transport;
import com.harshi.InventoryAndBilling.entities.TransportAndBuiltNumber;
import com.harshi.InventoryAndBilling.entities.Warehouse;
import com.harshi.InventoryAndBilling.repo.TransportAndBuiltNumberRepository;
import com.harshi.InventoryAndBilling.service.OrderService;
import com.harshi.InventoryAndBilling.service.PartyService;
import com.harshi.InventoryAndBilling.service.ProductService;
import com.harshi.InventoryAndBilling.service.RepsService;
import com.harshi.InventoryAndBilling.service.TransportService;

import ch.qos.logback.core.model.Model;

@Controller
public class OrderController {

	@Autowired
	private ProductService productService;

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	private RepsService repsService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private PartyService partyService;
	
	@Autowired
	private TransportService transportService;
	
	@Autowired
	private TransportAndBuiltNumberRepository transportAndBuiltNumberRepo;

	@GetMapping("/showBookOrder")
	public String showBookOrder(ModelMap modelMap) {
		// 1. Get the current date
		LocalDate currentDate = LocalDate.now();
		modelMap.addAttribute("currentDate", currentDate);

		// 2. Get the list of representatives from repsService
		List<Reps> repsList = repsService.getRepsList();
		modelMap.addAttribute("repsList", repsList);

		// Return the view name where users can select a representative
		return "orderView/bookOrderSelectReps";
	}

	@PostMapping("/bookOrderSaveReps")
	public String bookOrderSaveReps(@RequestParam("selectedRep") Long selectedRepId,
			@RequestParam("currentDate") LocalDate currentDate, ModelMap modelMap) {
		// Create a new Order object
		Order order = new Order();
		order.setOrderDate(Date.valueOf(currentDate));

		// Get the selected representative by ID
		Reps selectedRep = repsService.getRepsById(selectedRepId);
		order.setReps(selectedRep);

		// Save the order to the database
		Order savedOrder = orderService.saveOrder(order);
		List<Party> partyList = partyService.getPartyList();
		modelMap.addAttribute("order", savedOrder);
		modelMap.addAttribute("partyList", partyList);

		// Redirect to the next step with the order ID
		return "orderView/bookOrderSelectParty";
	}

	@PostMapping("/bookOrderSaveParty")
	public String bookOrderSaveParty(@RequestParam("selectedParty") Long selectedPartyId,
			@RequestParam("orderId") Long orderId, ModelMap modelMap) {
		// Get the order by ID
		Order order = orderService.getOrderById(orderId);

		// Get the party by ID
		Party selectedParty = partyService.getPartyById(selectedPartyId);

		// Set the received party in the received order
		order.setParty(selectedParty);

		// Update the order with the new party
		Order updatedOrder = orderService.saveOrder(order);
		List<Product> productsList = productService.getProductsList();

		modelMap.addAttribute("order", updatedOrder);
		modelMap.addAttribute("productsList", productsList);

		// Redirect to the next step or view
		return "orderView/bookOrderSelectProduct";
	}

	@PostMapping("/bookOrderShowTotalProductQuantity")
	public String bookOrderShowTotalProductQuantity(@RequestParam("selectedProduct") Long selectedProductId,
			@RequestParam("orderId") Long orderId, ModelMap modelMap) {
		// Get the order by ID
		Order order = orderService.getOrderById(orderId);

		// Get the selected product by ID
		Product selectedProduct = productService.getProductById(selectedProductId);

		// Calculate total quantities from warehouseQuantities (you need to implement
		// this logic)
		BigDecimal totalQuantitiesInWarehouse = calculateTotalQuantities(selectedProduct.getWarehouseQuantities());

		modelMap.addAttribute("order", order);
		modelMap.addAttribute("selectedProduct", selectedProduct);
		modelMap.addAttribute("totalQuantitiesInWarehouse", totalQuantitiesInWarehouse);

		return "orderView/bookOrderCreateOrderLineItems";
	}

	private BigDecimal calculateTotalQuantities(Map<Warehouse, Integer> warehouseQuantities) {
		Long totalQuantitiesInWarehouse = 0L;
		for (Entry<Warehouse, Integer> entry : warehouseQuantities.entrySet()) {
			totalQuantitiesInWarehouse += entry.getValue();
		}
		return BigDecimal.valueOf(totalQuantitiesInWarehouse);
	}

	@PostMapping("/bookOrderSaveOrderLineItems")
	public String bookOrderSaveOrderLineItems(@RequestParam("productId") Long selectedProductId,
			@RequestParam("orderId") Long orderId, @RequestParam("rate") BigDecimal rate,
			@RequestParam("quantity") int quantity, ModelMap modelMap) {

		// Get the order by ID
		Order order = orderService.getOrderById(orderId);

		// Get the selected product by ID
		Product selectedProduct = productService.getProductById(selectedProductId);

		// Create a new order line item
		OrderLineItem lineItem = new OrderLineItem();
		lineItem.setProduct(selectedProduct);
		lineItem.setRate(rate);
		lineItem.setQuantity(quantity);

		// Calculate total price for the line item
		BigDecimal totalPrice = rate.multiply(BigDecimal.valueOf(quantity));

		// Add the line item to the order
		order.addLineItem(lineItem);

		// Update the warehouse quantities
		updateProductQuantites(quantity, selectedProduct);

		// Update the order with the new line item and product
		Order updatedOrder = orderService.saveOrder(order);
		List<Transport> transportsList = transportService.getTransportList();

		modelMap.addAttribute("order", updatedOrder);
		modelMap.addAttribute("lineItem", lineItem);
		modelMap.addAttribute("totalPrice", totalPrice);
		modelMap.addAttribute("transportsList", transportsList);

		// Redirect to the next step or view
		return "orderView/bookOrderSelectTransport";
		// Replace with the appropriate next view
	}

	private void updateProductQuantites(int quantity, Product selectedProduct) {
		Map<Warehouse, Integer> warehouseQuantities = selectedProduct.getWarehouseQuantities();
		for (Entry<Warehouse, Integer> entry : warehouseQuantities.entrySet()) {
			Warehouse warehouse = entry.getKey();
			Integer availableQuantity = entry.getValue();

			// Calculate how much can be deducted from this warehouse
			int quantityToDeduct = Math.min(availableQuantity, quantity);

			// Deduct the quantity from this warehouse
			warehouseQuantities.put(warehouse, availableQuantity - quantityToDeduct);

			// Reduce the total quantity
			quantity -= quantityToDeduct;

			// If quantity is now zero, break the loop
			if (quantity == 0) {
				break;
			}
		}

		// Set the updated warehouseQuantities to the selectedProduct
		selectedProduct.setWarehouseQuantities(warehouseQuantities);
	}
	
	@PostMapping("/bookOrderSaveTransport")
	public String bookOrderSaveTransport(@RequestParam("selectedTransport") Long selectedTransportId,
	        @RequestParam("orderId") Long orderId, ModelMap modelMap) {

	    // Get the order by ID
	    Order order = orderService.getOrderById(orderId);

	    // Get the selected transport by ID
	    Transport selectedTransport = transportService.getTransportById(selectedTransportId);

	    // Create a new TransportAndBuiltNumber and set its values
	    TransportAndBuiltNumber transportAndBuiltNumber = new TransportAndBuiltNumber();
	    transportAndBuiltNumber.setTransport(selectedTransport);
	    transportAndBuiltNumber.setBuiltNumber("NotAlloted");

	    // Save the TransportAndBuiltNumber to the database
	    transportAndBuiltNumber = transportAndBuiltNumberRepo.save(transportAndBuiltNumber);

	    // Set the saved transport in the received order
	    order.setTransportAndBuiltNumber(transportAndBuiltNumber);

	    // Update the order with the new transport
	    Order updatedOrder = orderService.saveOrder(order);

	    modelMap.addAttribute("msg", "Order saved with " + updatedOrder.getOrderId());

	    // Redirect to the next step or view
	    return "dashboardView/landing"; // Replace with the appropriate next view
	}
	
	// Method to show the order list
    @RequestMapping("/showOrderList")
    public String showOrderList(ModelMap modelMap) {
        List<Order> orderList = orderService.getAllOrders();
        modelMap.addAttribute("orderList", orderList);
        return "orderView/orderList";
    }

    // Method to show order details by orderId
    @GetMapping("/orderDetails/{orderId}")
    public String showOrderDetails(@PathVariable Long orderId, ModelMap modelMap) {
        Order order = orderService.getOrderById(orderId);
        modelMap.addAttribute("order", order);
        return "orderView/orderDetails";
    }



}
