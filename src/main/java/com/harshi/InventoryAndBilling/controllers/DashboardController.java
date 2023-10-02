package com.harshi.InventoryAndBilling.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.harshi.InventoryAndBilling.service.PaymentService;

@Controller
public class DashboardController {

	@Autowired
	private PaymentService paymentService;

	private static final Logger LOGGER = LoggerFactory.getLogger(DashboardController.class);

	@RequestMapping("/showTotalOutstanding")
	public String showTotalOutstanding(ModelMap modelMap) {
		LOGGER.info("Inside getTotalOutstanding() on DashboardController");
		modelMap.addAttribute("totalOutstanding", paymentService.getTotalOutstanding());
		return "dashboard/totalOutstanding";
	}

	@RequestMapping(value = "/showLanding")
	public String login(ModelMap modelMap) {
		LOGGER.info("Redirecting landing.html");
		modelMap.addAttribute("msg", ".....On Home Page.....");
		return "dashboard/landing";
	}

}
