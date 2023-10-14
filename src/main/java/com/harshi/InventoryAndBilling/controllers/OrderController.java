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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.harshi.InventoryAndBilling.entities.Order;
import com.harshi.InventoryAndBilling.entities.Party;
import com.harshi.InventoryAndBilling.entities.Reps;
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

    // Other methods...

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
