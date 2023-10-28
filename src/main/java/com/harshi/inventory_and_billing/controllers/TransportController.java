package com.harshi.inventory_and_billing.controllers;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.harshi.inventory_and_billing.entities.Transport;
import com.harshi.inventory_and_billing.service.TransportService;

/**
 * Controller class for managing transport-related operations.
 */
@Controller
public class TransportController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransportController.class);

    @Autowired
    private TransportService transportService;

    /**
     * Display the list of transports.
     *
     * @param modelMap ModelMap for adding attributes to the view.
     * @return The view name for the transport list.
     */
    @RequestMapping("/showTransportList")
    public String showTransportList(ModelMap modelMap) {
        LOGGER.info("Displaying the list of transports");
        List<Transport> transportList = transportService.getTransportList();
        modelMap.addAttribute("transportList", transportList);
        return "transportView/transportList";
    }

    /**
     * Display the details of a specific transport by transportId.
     *
     * @param transportId The ID of the transport to display.
     * @param model Model for adding attributes to the view.
     * @return The view name for the transport details.
     */
    @GetMapping("/transportDetails/{transportId}")
    public String showTransportDetails(@PathVariable Long transportId, Model model) {
        LOGGER.info("Displaying transport details for transport with ID: {}", transportId);
        Transport transport = transportService.getTransportById(transportId);
        model.addAttribute("transport", transport);
        return "transportView/transportDetails";
    }

    /**
     * Display the form for adding new transports.
     *
     * @param model Model for adding attributes to the view.
     * @return The view name for the add transport form.
     */
    @RequestMapping("/showAddTransport")
    public String showAddTransport(Model model) {
        LOGGER.info("Displaying the add transport form");
        model.addAttribute("transport", new Transport());
        return "transportView/addTransport";
    }

    /**
     * Handle the addition of new transports.
     *
     * @param transport   The Transport object to be added.
     * @param modelMap ModelMap for adding attributes to the view.
     * @return The view name for the transport list or the error view in case of an exception.
     */
    @PostMapping("/addTransport")
    public String addTransport(@ModelAttribute("transport") Transport transport, ModelMap modelMap) {
        LOGGER.info("Saving a new transport");
        try {
            // Convert transport name to uppercase
            transport.setTransportName(transport.getTransportName().toUpperCase());

            // Attempt to save the transport
            Transport savedTransport = transportService.saveTransport(transport);
            List<Transport> transportList = transportService.getTransportList();
            modelMap.addAttribute("transportList", transportList);
            String msg = "Transport saved with ID - " + savedTransport.getTransportId();
            modelMap.addAttribute("msg", msg);
            return "transportView/transportList";
        } catch (Exception e) {
            // Handle the exception
            e.printStackTrace(); // You can log the exception for debugging purposes

            // Add an error message to the model to display to the user
            modelMap.addAttribute("error", "An error occurred while saving the transport. Please try again.");
            modelMap.addAttribute("errorMessage", e.getMessage());
            return "error"; // Return to the error view with the error message
        }
    }

}
