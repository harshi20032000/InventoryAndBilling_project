package com.harshi.inventory_and_billing.controllers;

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

import com.harshi.inventory_and_billing.entities.Order;
import com.harshi.inventory_and_billing.entities.Reps;
import com.harshi.inventory_and_billing.helper.OrderHelper;
import com.harshi.inventory_and_billing.service.PartyService;
import com.harshi.inventory_and_billing.service.RepsService;

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
    @ModelAttribute("reps")
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
        modelMap.addAttribute("repsList", repsList);
        return "repsView/repsList";
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
        return "repsView/addReps";
    }

    /**
     * Handle the addition of new representatives.
     *
     * @param reps     The Reps object to be added.
     * @param modelMap ModelMap for adding attributes to the view.
     * @return The view name for the representative list.
     */
    @RequestMapping("/addReps")
    public String addReps(@ModelAttribute("reps") Reps reps, ModelMap modelMap) {
        LOGGER.info("Saving a new representative");
        Reps savedReps = repsService.saveReps(reps);
        List<Reps> repsList = repsService.getRepsList();
        modelMap.addAttribute("repsList", repsList);
        String msg = "Representative saved with ID - " + savedReps.getRepId();
        modelMap.addAttribute("msg", msg);
        return "repsView/repsList";
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
		model.addAttribute("totalBillAmount", totalBillAmount);
		model.addAttribute("remainingBillAmount", remainingBillAmount);
        model.addAttribute("orders", orderList);
        model.addAttribute("reps", reps);
        return "repsView/repsDetails";
    }
}
