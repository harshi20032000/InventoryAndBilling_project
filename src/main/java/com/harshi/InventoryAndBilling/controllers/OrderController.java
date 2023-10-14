package com.harshi.InventoryAndBilling.controllers;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
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

/**
 * Controller class for managing order-related operations.
 */
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

    /**
     * Display the initial page for booking an order.
     *
     * @param modelMap ModelMap for adding attributes to the view.
     * @return The view name for selecting a representative.
     */
    @GetMapping("/showBookOrder")
    public String showBookOrder(ModelMap modelMap) {
        LOGGER.info("Entering showBookOrder");
        // 1. Get the current date
        LocalDate currentDate = LocalDate.now();
        modelMap.addAttribute("currentDate", currentDate);

        // 2. Get the list of representatives from repsService
        List<Reps> repsList = repsService.getRepsList();
        modelMap.addAttribute("repsList", repsList);

        // Return the view name where users can select a representative
        return "orderView/bookOrderSelectReps";
    }

    /**
     * Save the selected representative for the order.
     *
     * @param selectedRepId The ID of the selected representative.
     * @param currentDate    The current date.
     * @param modelMap       ModelMap for adding attributes to the view.
     * @return The view name for selecting a party.
     */
    @PostMapping("/bookOrderSaveReps")
    public String bookOrderSaveReps(@RequestParam("selectedRep") Long selectedRepId,
            @RequestParam("currentDate") LocalDate currentDate, ModelMap modelMap) {
        LOGGER.info("Saving selected representative with ID: {}", selectedRepId);
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
    /**
     * Saves the selected party for a specific order.
     *
     * @param selectedPartyId The ID of the selected party.
     * @param orderId The ID of the order.
     * @param modelMap The model map for storing attributes.
     * @return The view for selecting products for the order.
     */
    @PostMapping("/bookOrderSaveParty")
    public String bookOrderSaveParty(@RequestParam("selectedParty") Long selectedPartyId,
            @RequestParam("orderId") Long orderId, ModelMap modelMap) {

        LOGGER.info("Saving selected party with ID: {} for order with ID: {}", selectedPartyId, orderId);
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

    /**
     * Calculates the total product quantities for the selected product in an order.
     *
     * @param selectedProductId The ID of the selected product.
     * @param orderId The ID of the order.
     * @param modelMap The model map for storing attributes.
     * @return The view for creating order line items.
     */
    @PostMapping("/bookOrderShowTotalProductQuantity")
    public String bookOrderShowTotalProductQuantity(@RequestParam("selectedProduct") Long selectedProductId,
            @RequestParam("orderId") Long orderId, ModelMap modelMap) {

        LOGGER.info("Calculating total quantities for selected product with ID: {} in order with ID: {}", selectedProductId, orderId);
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

    /**
     * Calculates the total quantities for a product based on warehouse quantities.
     *
     * @param warehouseQuantities A map of warehouse quantities.
     * @return The total quantities as a BigDecimal.
     */
    private BigDecimal calculateTotalQuantities(Map<Warehouse, Integer> warehouseQuantities) {

		Long totalQuantitiesInWarehouse = 0L;
		for (Entry<Warehouse, Integer> entry : warehouseQuantities.entrySet()) {
			totalQuantitiesInWarehouse += entry.getValue();
		}
		return BigDecimal.valueOf(totalQuantitiesInWarehouse);
    }

    /**
     * Saves order line items for a product in an order.
     *
     * @param selectedProductId The ID of the selected product.
     * @param orderId The ID of the order.
     * @param rate The rate for the product.
     * @param quantity The quantity of the product.
     * @param modelMap The model map for storing attributes.
     * @return The view for selecting transport for the order.
     */
    @PostMapping("/bookOrderSaveOrderLineItems")
    public String bookOrderSaveOrderLineItems(@RequestParam("productId") Long selectedProductId,
            @RequestParam("orderId") Long orderId, @RequestParam("rate") BigDecimal rate,
            @RequestParam("quantity") int quantity, ModelMap modelMap) {

        LOGGER.info("Saving order line items for product with ID: {} in order with ID: {}", selectedProductId, orderId);

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

		// Update the warehouse quantities and get orderWarehouseQuantities
		Map<Warehouse, Integer> orderWarehouseQuantities = updateProductQuantities(quantity, selectedProduct);

		// Set orderWarehouseQuantities in the line item
		lineItem.setOrderWarehouseQuantities(orderWarehouseQuantities);

		// Add the line item to the order
		order.addLineItem(lineItem);

		// Update the order with the new line item and product
		Order updatedOrder = orderService.saveOrder(order);
		List<Transport> transportsList = transportService.getTransportList();

		modelMap.addAttribute("order", updatedOrder);
		modelMap.addAttribute("lineItem", lineItem);
		modelMap.addAttribute("totalPrice", totalPrice);
		modelMap.addAttribute("transportsList", transportsList);

		// Redirect to the next step or view
		return "orderView/bookOrderSelectTransport";
    }

    /**
     * Updates the product quantities based on order line items.
     *
     * @param quantity The quantity to update.
     * @param selectedProduct The selected product.
     * @return A map of warehouse quantities for the order.
     */
    private Map<Warehouse, Integer> updateProductQuantities(int quantity, Product selectedProduct) {

		Map<Warehouse, Integer> warehouseQuantities = selectedProduct.getWarehouseQuantities();
		Map<Warehouse, Integer> orderWarehouseQuantities = new HashMap<>();
		for (Entry<Warehouse, Integer> entry : warehouseQuantities.entrySet()) {
			Warehouse warehouse = entry.getKey();
			Integer availableQuantity = entry.getValue();

			// Calculate how much can be deducted from this warehouse
			int quantityToDeduct = Math.min(availableQuantity, quantity);

			// Deduct the quantity from this warehouse
			warehouseQuantities.put(warehouse, availableQuantity - quantityToDeduct);
			// Put the quantity of product deducted from this warehouse into the new field
			// to be returned to show for each OrderDetails.
			orderWarehouseQuantities.put(warehouse, quantityToDeduct);

			// Reduce the total quantity
			quantity -= quantityToDeduct;

			// If quantity is now zero, break the loop
			if (quantity == 0) {
				break;
			}
		}

		// Set the updated warehouseQuantities to the selectedProduct
		selectedProduct.setWarehouseQuantities(warehouseQuantities);
		// returns the quantities of product deducted from each warehouse for a specific
		// order to track for future reference.
		return orderWarehouseQuantities;
    }

    /**
     * Saves the selected transport for an order.
     *
     * @param selectedTransportId The ID of the selected transport.
     * @param orderId The ID of the order.
     * @param modelMap The model map for storing attributes.
     * @return The landing page or the appropriate next view.
     */
    @PostMapping("/bookOrderSaveTransport")
    public String bookOrderSaveTransport(@RequestParam("selectedTransport") Long selectedTransportId,
            @RequestParam("orderId") Long orderId, ModelMap modelMap) {

        LOGGER.info("Saving selected transport with ID: {} for order with ID: {}", selectedTransportId, orderId);

		// Get the order by ID
		Order order = orderService.getOrderById(orderId);

		// Get the selected transport by ID
		Transport selectedTransport = transportService.getTransportById(selectedTransportId);

		// Create a new TransportAndBuiltNumber and set its values
		TransportAndBuiltNumber transportAndBuiltNumber = new TransportAndBuiltNumber();
		transportAndBuiltNumber.setTransport(selectedTransport);
		transportAndBuiltNumber.setBuiltNumber("NotAlloted" + order.getOrderId());

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


    /**
     * Display the order list.
     *
     * @param modelMap ModelMap for adding attributes to the view.
     * @return The view name for the order list.
     */
    @RequestMapping("/showOrderList")
    public String showOrderList(ModelMap modelMap) {
        LOGGER.info("Displaying order list");
        List<Order> orderList = orderService.getAllOrders();
        modelMap.addAttribute("orderList", orderList);
        return "orderView/orderList";
    }

    /**
     * Display the details of a specific order.
     *
     * @param orderId  The ID of the order to display.
     * @param modelMap ModelMap for adding attributes to the view.
     * @return The view name for the order details.
     */
    @GetMapping("/orderDetails/{orderId}")
    public String showOrderDetails(@PathVariable Long orderId, ModelMap modelMap) {
        LOGGER.info("Displaying order details for order with ID: {}", orderId);
        Order order = orderService.getOrderById(orderId);
        modelMap.addAttribute("order", order);
        return "orderView/orderDetails";
    }
}
