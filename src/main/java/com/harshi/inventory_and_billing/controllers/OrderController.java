package com.harshi.inventory_and_billing.controllers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.harshi.inventory_and_billing.entities.Order;
import com.harshi.inventory_and_billing.entities.OrderLineItem;
import com.harshi.inventory_and_billing.entities.Party;
import com.harshi.inventory_and_billing.entities.Payment;
import com.harshi.inventory_and_billing.entities.Product;
import com.harshi.inventory_and_billing.entities.Reps;
import com.harshi.inventory_and_billing.entities.Transport;
import com.harshi.inventory_and_billing.entities.TransportAndBuiltNumber;
import com.harshi.inventory_and_billing.entities.Warehouse;
import com.harshi.inventory_and_billing.helper.OrderHelper;
import com.harshi.inventory_and_billing.repo.TransportAndBuiltNumberRepository;
import com.harshi.inventory_and_billing.service.OrderService;
import com.harshi.inventory_and_billing.service.OrderStatusHistoryService;
import com.harshi.inventory_and_billing.service.PartyService;
import com.harshi.inventory_and_billing.service.PaymentService;
import com.harshi.inventory_and_billing.service.ProductService;
import com.harshi.inventory_and_billing.service.RepsService;
import com.harshi.inventory_and_billing.service.TransportService;
import com.harshi.inventory_and_billing.service.WarehouseService;

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
	private PaymentService paymentService;

	@Autowired
	private TransportAndBuiltNumberRepository transportAndBuiltNumberRepo;

	@Autowired
	private OrderStatusHistoryService orderStatusHistoryService;
	
	@Autowired
	private WarehouseService warehouseService;

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
		// Add the order to the model for reference
		modelMap.addAttribute("repsList", repsList);

		// Return the view name where users can select a representative
		return "orderView/bookOrderSelectReps";
	}

	/**
	 * Save the selected representative for the order.
	 *
	 * @param selectedRepId The ID of the selected representative.
	 * @param currentDate   The current date.
	 * @param modelMap      ModelMap for adding attributes to the view.
	 * @return The view name for selecting a party.
	 */
	@PostMapping("/bookOrderSaveReps")
	public String bookOrderSaveReps(@RequestParam("selectedRep") Long selectedRepId,
			@RequestParam("currentDate") LocalDate currentDate, ModelMap modelMap) {
		LOGGER.info("Saving selected representative with ID: {}", selectedRepId);
		// Create a new Order object
		Order order = new Order();
		order.setOrderDate(currentDate);

		// Get the selected representative by ID
		Reps selectedRep = repsService.getRepsById(selectedRepId);
		order.setReps(selectedRep);

		// Save the order to the database
		Order savedOrder = orderService.saveOrder(order);
		List<Party> partyList = partyService.getPartyList();
		// Add the order to the model for reference
		modelMap.addAttribute("order", savedOrder);
		modelMap.addAttribute("partyList", partyList);

		// Redirect to the next step with the order ID
		return "orderView/bookOrderSelectParty";
	}

	/**
	 * Saves the selected party for a specific order.
	 *
	 * @param selectedPartyId The ID of the selected party.
	 * @param orderId         The ID of the order.
	 * @param modelMap        The model map for storing attributes.
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
		// Add the order to the model for reference
		modelMap.addAttribute("order", updatedOrder);
		modelMap.addAttribute("productsList", productsList);

		// Redirect to the next step or view
		return "orderView/bookOrderSelectProduct";
	}

	/**
	 * Calculates the total product quantities for the selected product in an order.
	 *
	 * @param selectedProductId The ID of the selected product.
	 * @param orderId           The ID of the order.
	 * @param modelMap          The model map for storing attributes.
	 * @return The view for creating order line items.
	 */
	@PostMapping("/bookOrderShowTotalProductQuantity")
	public String bookOrderShowTotalProductQuantity(@RequestParam("selectedProduct") Long selectedProductId,
			@RequestParam("orderId") Long orderId, ModelMap modelMap) {

		LOGGER.info("Calculating total quantities for selected product with ID: {} in order with ID: {}",
				selectedProductId, orderId);
		// Get the order by ID
		Order order = orderService.getOrderById(orderId);

		// Get the selected product by ID
		Product selectedProduct = productService.getProductById(selectedProductId);

		// Calculate total quantities from warehouseQuantities (you need to implement
		// this logic)
		BigDecimal totalQuantitiesInWarehouse = OrderHelper
				.calculateTotalQuantities(selectedProduct.getWarehouseQuantities());

		// Calculate total price for the line item
		BigDecimal totalPrice = OrderHelper.totalOrderPrice(order);
		Integer totalQuantities = OrderHelper.totalOrderQuantity(order);
		// Add the order to the model for reference
		modelMap.addAttribute("order", order);
		modelMap.addAttribute("selectedProduct", selectedProduct);
		modelMap.addAttribute("totalQuantitiesInWarehouse", totalQuantitiesInWarehouse);
		modelMap.addAttribute("totalPrice", totalPrice);
		modelMap.addAttribute("totalQuantities", totalQuantities);

		return "orderView/bookOrderCreateOrderLineItems";
	}

	/**
	 * Saves order line items for a product in an order.
	 *
	 * @param selectedProductId The ID of the selected product.
	 * @param orderId           The ID of the order.
	 * @param rate              The rate for the product.
	 * @param quantity          The quantity of the product.
	 * @param modelMap          The model map for storing attributes.
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
		
		// Get the main warehouse by code
		Warehouse mainWarehouse = warehouseService.getWarehouseById(1L);

		// Create a new order line item
		OrderLineItem lineItem = new OrderLineItem();
		lineItem.setProduct(selectedProduct);
		lineItem.setRate(rate);
		lineItem.setQuantity(quantity);

		// Update the warehouse quantities and get orderWarehouseQuantities
		Map<Warehouse, Integer> orderWarehouseQuantities = OrderHelper.updateProductQuantities(quantity,
				selectedProduct, order, mainWarehouse);

		// Set orderWarehouseQuantities in the line item
		lineItem.setOrderWarehouseQuantities(orderWarehouseQuantities);

		// Add the line item to the order
		order.addLineItem(lineItem);

		// Update the order with the new line item and product
		Order updatedOrder = orderService.saveOrder(order);

		// Calculate total price for the order
		BigDecimal totalPrice = OrderHelper.totalOrderPrice(order);
		Integer totalQuantities = OrderHelper.totalOrderQuantity(order);

		// Set totalBillAmount and remainingBillAmount
		BigDecimal totalBillAmount = totalPrice;
		BigDecimal remainingBillAmount = totalBillAmount;

		updatedOrder.setTotalBillAmount(totalBillAmount);
		updatedOrder.setRemainingBillAmount(remainingBillAmount);

		// Update the order with the new line item and product
		updatedOrder = orderService.saveOrder(order);

		// Add the order to the model for reference
		modelMap.addAttribute("order", updatedOrder);
		modelMap.addAttribute("lineItem", lineItem);
		modelMap.addAttribute("totalPrice", totalPrice);
		modelMap.addAttribute("totalQuantities", totalQuantities);

		// Redirect to the next step or view
		return "orderView/bookOrderSelectMoreProducts";
	}

	@PostMapping("/bookOrderShowSelectProducts")
	public String bookOrderShowSelectProducts(@RequestParam("orderId") Long orderId, ModelMap modelMap) {

		// Get the order by ID
		Order order = orderService.getOrderById(orderId);
		List<Product> productsList = productService.getProductsList();

		// Calculate total price for the line item
		BigDecimal totalPrice = OrderHelper.totalOrderPrice(order);
		Integer totalQuantities = OrderHelper.totalOrderQuantity(order);
		// Add the order to the model for reference
		modelMap.addAttribute("order", order);
		modelMap.addAttribute("productsList", productsList);
		modelMap.addAttribute("totalPrice", totalPrice);
		modelMap.addAttribute("totalQuantities", totalQuantities);

		// Redirect to the next step or view
		return "orderView/bookOrderSelectProduct";
	}

	@PostMapping("/bookOrderShowSelectTransport")
	public String bookOrderShowSelectTransport(@RequestParam("orderId") Long orderId, ModelMap modelMap) {

		// Get the order by ID
		Order order = orderService.getOrderById(orderId);
		List<Transport> transportsList = transportService.getTransportList();

		// Calculate total price for the line item
		BigDecimal totalPrice = OrderHelper.totalOrderPrice(order);
		Integer totalQuantities = OrderHelper.totalOrderQuantity(order);
		// Add the order to the model for reference
		modelMap.addAttribute("totalPrice", totalPrice);
		modelMap.addAttribute("totalQuantities", totalQuantities);
		modelMap.addAttribute("transportsList", transportsList);
		modelMap.addAttribute("order", order);

		// Redirect to the next step or view
		return "orderView/bookOrderSelectTransport";
	}

	/**
	 * Saves the selected transport for an order.
	 *
	 * @param selectedTransportId The ID of the selected transport.
	 * @param orderId             The ID of the order.
	 * @param modelMap            The model map for storing attributes.
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
		transportAndBuiltNumber.setBuiltNumber("NotAlloted_Oid-" + order.getOrderId());

		// Save the TransportAndBuiltNumber to the database
		transportAndBuiltNumber = transportAndBuiltNumberRepo.save(transportAndBuiltNumber);

		// Set the saved transport in the received order
		order.setTransportAndBuiltNumber(transportAndBuiltNumber);

		// Update the ordrStatus in the received order
		orderStatusHistoryService.addStatusChangeToOrder(order, "Order Placed_Oid-" + order.getOrderId());

		// Update the order with the new transport
		Order updatedOrder = orderService.saveOrder(order);
		// Add the msg to the model for reference
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
		// Calculate total price for the line item
		BigDecimal totalBillAmount = OrderHelper.totalOrderPrice(orderList);
		BigDecimal totalRemainingAmount = OrderHelper.totalPendingPrice(orderList);
		// Add the order to the model for reference
		modelMap.addAttribute("totalBillAmount", totalBillAmount);
		modelMap.addAttribute("totalRemainingAmount", totalRemainingAmount);
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

		// Calculate total price for the line item
		BigDecimal totalAmount = OrderHelper.totalOrderPrice(order);
		BigDecimal remainingAmount = OrderHelper.totalPendingPrice(order, totalAmount);
		Integer totalQuantities = OrderHelper.totalOrderQuantity(order);
		// Add the order to the model for reference
		modelMap.addAttribute("totalAmount", totalAmount);
		modelMap.addAttribute("remainingAmount", remainingAmount);
		modelMap.addAttribute("totalQuantities", totalQuantities);
		modelMap.addAttribute("order", order);
		return "orderView/orderDetails";
	}

	@GetMapping("/showEditOrderTransport/{orderId}")
	public String showEditOrderTransport(@PathVariable Long orderId, ModelMap model) {
		// Retrieve the order by ID
		Order order = orderService.getOrderById(orderId);
		List<Transport> transportList = transportService.getTransportList();

		// Add the order to the model for editing
		// Calculate total price for the line item
		BigDecimal totalAmount = OrderHelper.totalOrderPrice(order);
		Integer totalQuantities = OrderHelper.totalOrderQuantity(order);
		BigDecimal remainingAmount = OrderHelper.totalPendingPrice(order, totalAmount);
		// Add the order to the model for reference
		model.addAttribute("totalAmount", totalAmount);
		model.addAttribute("remainingAmount", remainingAmount);
		model.addAttribute("totalQuantities", totalQuantities);
		model.addAttribute("order", order);
		model.addAttribute("transportList", transportList);

		return "orderView/editOrderTransport";
	}

	@PostMapping("/editOrderTransport")
	public String editOrderTransport(@RequestParam("orderId") Long orderId,
			@RequestParam("transportId") Long transportId, @RequestParam("biltyNumber") String biltyNumber,
			ModelMap modelMap) {
		// Update the order in the database
		biltyNumber = biltyNumber.toUpperCase();
		Order updatedOrder = orderService.updateOrderBiltyNo(orderId, transportId, biltyNumber);
		// Calculate total price for the line item
		BigDecimal totalAmount = OrderHelper.totalOrderPrice(updatedOrder);
		BigDecimal remainingAmount = OrderHelper.totalPendingPrice(updatedOrder, totalAmount);
		Integer totalQuantities = OrderHelper.totalOrderQuantity(updatedOrder);
		// Add the order to the model for reference
		modelMap.addAttribute("totalAmount", totalAmount);
		modelMap.addAttribute("remainingAmount", remainingAmount);
		modelMap.addAttribute("totalQuantities", totalQuantities);
		modelMap.addAttribute("order", updatedOrder);

		// Redirect to the order details page or order list page
		return "orderView/orderDetails";
	}

	@GetMapping("/showAddOrderPayment/{orderId}")
	public String showAddOrderPayment(@PathVariable Long orderId, ModelMap model) {
		// Retrieve the order by ID
		Order order = orderService.getOrderById(orderId);
		// Calculate total price for the line item
		BigDecimal totalAmount = OrderHelper.totalOrderPrice(order);
		BigDecimal remainingAmount = OrderHelper.totalPendingPrice(order, totalAmount);
		Integer totalQuantities = OrderHelper.totalOrderQuantity(order);
		// Add the order to the model for reference
		model.addAttribute("totalAmount", totalAmount);
		model.addAttribute("remainingAmount", remainingAmount);
		model.addAttribute("totalQuantities", totalQuantities);
		model.addAttribute("order", order);
		model.addAttribute("payment", new Payment());

		return "orderView/addOrderPayment";
	}

	@PostMapping("/addOrderPayment")
	public String addOrderPayment(@ModelAttribute("payment") Payment payment, @RequestParam Long orderId,
			ModelMap modelMap, @RequestParam("payDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate payDate) {
		// Find the order by orderId
		Order order = orderService.getOrderById(orderId);

		// Set the order for the payment
		payment.setOrder(order);

		// Save the payment
		payment.setPayDate(payDate);
		Payment savedPayment = paymentService.savePayment(payment);

		// Add the saved payment to the order's payment list
		order.addPayment(savedPayment);

		// Update the ordrStatus in the received order
		orderStatusHistoryService.addStatusChangeToOrder(order, "Payment Received of - " + savedPayment.getPayAmount());
		// Update remainingBillAmount when a payment is added, Later change with
		// order.getTotalBillAmount(); and test
		BigDecimal totalBillAmount = OrderHelper.totalOrderPrice(order);
		BigDecimal remainingBillAmount = totalBillAmount.subtract(BigDecimal.valueOf(savedPayment.getPayAmount()));
		order.setRemainingBillAmount(remainingBillAmount);
		// Update the order
		Order updatedOrder = orderService.saveOrder(order);
		// Calculate total price for the line item
		BigDecimal totalAmount = OrderHelper.totalOrderPrice(updatedOrder);
		BigDecimal remainingAmount = OrderHelper.totalPendingPrice(updatedOrder, totalAmount);
		Integer totalQuantities = OrderHelper.totalOrderQuantity(updatedOrder);
		// Add the order to the model for reference
		modelMap.addAttribute("totalAmount", totalAmount);
		modelMap.addAttribute("remainingAmount", remainingAmount);
		modelMap.addAttribute("totalQuantities", totalQuantities);
		modelMap.addAttribute("order", updatedOrder);
		return "orderView/orderDetails";
	}
}
