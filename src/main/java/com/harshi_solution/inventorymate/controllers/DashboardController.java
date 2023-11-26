package com.harshi_solution.inventorymate.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.harshi_solution.inventorymate.service.PaymentService;
import com.harshi_solution.inventorymate.util.Constants;

/**
 * Controller class for managing the dashboard and related views.
 */
@Controller
public class DashboardController {


	@Autowired
    private PaymentService paymentService;

    private static final Logger LOGGER = LoggerFactory.getLogger(DashboardController.class);

    /**
     * Display the total outstanding amount on the dashboard.
     *
     * @param modelMap ModelMap for adding attributes to the view.
     * @return The view name for the total outstanding display.
     */
    @RequestMapping("/showTotalOutstanding")
    public String showTotalOutstanding(ModelMap modelMap) {
        LOGGER.info("Inside getTotalOutstanding() on DashboardController");
        modelMap.addAttribute("totalOutstanding", paymentService.getTotalOutstanding());
        return "dashboardView/totalOutstanding";
    }

    /**
     * Redirect to the landing page.
     *
     * @param modelMap ModelMap for adding attributes to the view.
     * @return The view name for the landing page.
     */
    @RequestMapping(value = "/showLanding")
    public String login(ModelMap modelMap) {
        LOGGER.info("Redirecting to landing.html");
        modelMap.addAttribute("msg", ".....On Home Page.....");
        return Constants.DASHBOARD_VIEW_FOLDER + Constants.LANDING;
    }
}
