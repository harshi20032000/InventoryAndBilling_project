package com.harshi_solution.inventorymate.controllers;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.harshi_solution.inventorymate.entities.Order;
import com.harshi_solution.inventorymate.entities.Reps;
import com.harshi_solution.inventorymate.helper.OrderHelper;
import com.harshi_solution.inventorymate.service.RepsService;
import com.harshi_solution.inventorymate.util.Constants;

/**
 * Controller class for managing representative-related operations.
 */
@Controller
public class RepsController {

  	private static final Logger LOGGER = LoggerFactory.getLogger(RepsController.class);

    @Autowired
    private RepsService repsService;

    /**
     * This is an empty reps attribute so that the form can use this while rendering.
     *
     * @return A new Reps instance.
     */
    @ModelAttribute(Constants.REPS)
    public Reps getReps() {
        return new Reps();
    }

    /**
     * Display the list of representatives.
     *
     * @param modelMap ModelMap for adding attributes to the view.
     * @return The view name for the representative list.
     */
    @RequestMapping("/showRepsList")
    public String showRepsList(ModelMap modelMap) {
        LOGGER.info("Displaying the list of representatives");
        List<Reps> repsList = repsService.getRepsList();
        modelMap.addAttribute(Constants.REPS_LIST, repsList);
        return Constants.REPS_VIEW+Constants.REPS_LIST;
    }

    /**
     * Display the form for adding new representatives.
     *
     * @param model Model for adding attributes to the view.
     * @return The view name for the add representatives form.
     */
    @RequestMapping("/showAddReps")
    public String showAddReps(Model model) {
        LOGGER.info("Displaying the add representatives form");
        return Constants.REPS_VIEW+"addReps";
    }

    /**
     * Handle the addition of new representatives.
     *
     * @param reps     The Reps object to be added.
     * @param modelMap ModelMap for adding attributes to the view.
     * @return The view name for the representative list or the error view in case of an exception.
     */
    @RequestMapping("/addReps")
    public String addReps(@ModelAttribute(Constants.REPS) Reps reps, ModelMap modelMap) {
        LOGGER.info("Saving a new representative");
        try {
            // Convert representative name and location to uppercase
            reps.setRepName(reps.getRepName().toUpperCase());
            reps.setRepLocation(reps.getRepLocation().toUpperCase());

            // Save the representative
            Reps savedReps = repsService.saveReps(reps);
            List<Reps> repsList = repsService.getRepsList();
            modelMap.addAttribute(Constants.REPS_LIST, repsList);
            String msg = "Representative saved with ID - " + savedReps.getRepId();
            modelMap.addAttribute(Constants.MSG, msg);
            return Constants.REPS_VIEW+Constants.REPS_LIST;
        } catch (Exception e) {
            // Handle the exception
            e.printStackTrace(); // You can log the exception for debugging purposes

            // Add an error message to the model to display to the user
            modelMap.addAttribute(Constants.ERROR, "An error occurred while saving the representative. Please try again.");
            modelMap.addAttribute(Constants.ERROR_MESSAGE, e.getMessage());
            return Constants.ERROR; // Return to the error view with the error message
        }
    }


    /**
     * Display the details of a specific representative by repId.
     *
     * @param repId The ID of the representative to display.
     * @param model Model for adding attributes to the view.
     * @return The view name for the representative details.
     */
    @GetMapping("/repsDetails/{repId}")
    public String showRepsDetails(@PathVariable Long repId, Model model) {
        LOGGER.info("Displaying representative details for representative with ID: {}", repId);
        Reps reps = repsService.getRepsById(repId);
        List<Order> orderList = repsService.getOrderListByReps(repId);
        // Calculate total price for the line item
		BigDecimal totalBillAmount = OrderHelper.totalOrderPrice(orderList);
		BigDecimal remainingBillAmount = OrderHelper.totalPendingPrice(orderList);
		// Add the order to the model for reference
		model.addAttribute(Constants.TOTAL_BILL_AMOUNT, totalBillAmount);
		model.addAttribute(Constants.REMAINING_BILL_AMOUNT, remainingBillAmount);
        model.addAttribute(Constants.ORDERS, orderList);
        model.addAttribute(Constants.REPS, reps);
        return Constants.REPS_VIEW+Constants.REPS_DETAILS;
    }
}
