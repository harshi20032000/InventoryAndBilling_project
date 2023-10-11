package com.harshi.InventoryAndBilling.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.harshi.InventoryAndBilling.entities.Transport;
import com.harshi.InventoryAndBilling.service.TransportService;

@Controller
public class TransportController {

    @Autowired
    private TransportService transportService;

    // Method to show the transport list
    @RequestMapping("/showTransportList")
    public String showTransportList(ModelMap modelMap) {
        List<Transport> transportList = transportService.getTransportList();
        modelMap.addAttribute("transportList", transportList);
        return "transportView/transportList";
    }

    // Method to show transport details by transportId
    @GetMapping("/transportDetails/{transportId}")
    public String showTransportDetails(@PathVariable Long transportId, Model model) {
        Transport transport = transportService.getTransportById(transportId);
        model.addAttribute("transport", transport);
        return "transportView/transportDetails";
    }

    // Method to display the add transport form
    @RequestMapping("/showAddTransport")
    public String showAddTransport(Model model) {
        model.addAttribute("transport", new Transport());
        return "transportView/addTransport";
    }

    // Method to handle transport addition
    @PostMapping("/addTransport")
    public String addTransport(@ModelAttribute("transport") Transport transport, ModelMap modelMap) {
        Transport savedTransport = transportService.saveTransport(transport);
        List<Transport> transportList = transportService.getTransportList();
        modelMap.addAttribute("transportList", transportList);
        String msg = "Transport saved with ID - " + savedTransport.getTransportId();
        modelMap.addAttribute("msg", msg);
        return "transportView/transportList";
    }
}
