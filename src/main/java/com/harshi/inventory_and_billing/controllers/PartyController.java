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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.harshi.inventory_and_billing.entities.Order;
import com.harshi.inventory_and_billing.entities.Party;
import com.harshi.inventory_and_billing.helper.OrderHelper;
import com.harshi.inventory_and_billing.service.PartyService;

/**
 * Controller class for managing party-related operations.
 */
@Controller
public class PartyController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PartyController.class);

	@Autowired
	private PartyService partyService;

	/**
	 * Display the list of parties.
	 *
	 * @param modelMap ModelMap for adding attributes to the view.
	 * @return The view name for the party list.
	 */
	@RequestMapping("/showPartyList")
	public String showPartyList(ModelMap modelMap) {
		LOGGER.info("Displaying the party list");
		List<Party> partyList = partyService.getPartyList();
		modelMap.addAttribute("partyList", partyList);
		return "partyView/partyList";
	}

	/**
	 * Display the details of a specific party by partyId.
	 *
	 * @param partyId The ID of the party to display.
	 * @param model   Model for adding attributes to the view.
	 * @return The view name for the party details.
	 */
	@GetMapping("/partyDetails/{partyId}")
	public String showPartyDetails(@PathVariable Long partyId, Model model) {
		LOGGER.info("Displaying party details for party with ID: {}", partyId);
		Party party = partyService.getPartyById(partyId);
		List<Order> orderList = partyService.getOrderListByParty(partyId);
		// Calculate total price for the line item
		BigDecimal totalBillAmount = OrderHelper.totalOrderPrice(orderList);
		BigDecimal remainingBillAmount = OrderHelper.totalPendingPrice(orderList);
		// Add the order to the model for reference
		model.addAttribute("totalBillAmount", totalBillAmount);
		model.addAttribute("remainingBillAmount", remainingBillAmount);
		model.addAttribute("orders", orderList);
		model.addAttribute("party", party);
		return "partyView/partyDetails";
	}

	/**
	 * Display the form for adding a new party.
	 *
	 * @param model Model for adding attributes to the view.
	 * @return The view name for the add party form.
	 */
	@RequestMapping("/showAddParty")
	public String showAddParty(Model model) {
		LOGGER.info("Displaying the add party form");
		model.addAttribute("party", new Party());
		return "partyView/addParty";
	}

	/**
	 * Handle the addition of a new party.
	 *
	 * @param party    The Party object to be added.
	 * @param modelMap ModelMap for adding attributes to the view.
	 * @return The view name for the party list.
	 */
	@PostMapping("/addParty")
	public String addParty(@ModelAttribute("party") Party party, ModelMap modelMap) {
		LOGGER.info("Saving a new party");
		Party savedParty = partyService.saveParty(party);
		List<Party> partyList = partyService.getPartyList();
		modelMap.addAttribute("partyList", partyList);
		String msg = "Party saved with ID - " + savedParty.getPartyId();
		modelMap.addAttribute("msg", msg);
		return "partyView/partyList";
	}
}
