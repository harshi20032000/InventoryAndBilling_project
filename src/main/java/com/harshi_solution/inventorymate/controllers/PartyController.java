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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.harshi_solution.inventorymate.entities.Order;
import com.harshi_solution.inventorymate.entities.Party;
import com.harshi_solution.inventorymate.helper.OrderHelper;
import com.harshi_solution.inventorymate.service.PartyService;
import com.harshi_solution.inventorymate.util.Constants;

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
		modelMap.addAttribute(Constants.PARTY_LIST, partyList);
		return Constants.PARTY_VIEW_FOLDER + Constants.PARTY_LIST;
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
		return Constants.PARTY_VIEW_FOLDER + Constants.PARTY_DETAILS;
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
		return Constants.PARTY_VIEW_FOLDER + Constants.ADD_PARTY;
	}

	/**
	 * Handle the addition of a new party.
	 *
	 * @param party    The Party object to be added.
	 * @param modelMap ModelMap for adding attributes to the view.
	 * @return The view name for the party list.
	 */
	@PostMapping(Constants.ADD_PARTY)
	public String addParty(@ModelAttribute("party") Party party, ModelMap modelMap) {
		LOGGER.info("Saving a new party");
		try {
			// Convert party name and location to uppercase
			party.setPartyName(party.getPartyName().toUpperCase());
			party.setPartyLocation(party.getPartyLocation().toUpperCase());

			// Attempt to save the party
			Party savedParty = partyService.saveParty(party);
			List<Party> partyList = partyService.getPartyList();
			modelMap.addAttribute(Constants.PARTY_LIST, partyList);
			String msg = "Party saved with ID - " + savedParty.getPartyId();
			modelMap.addAttribute("msg", msg);
			return Constants.PARTY_VIEW_FOLDER + Constants.PARTY_LIST;
		} catch (Exception e) {
			// Handle the exception
			e.printStackTrace(); // You can log the exception for debugging purposes

			// Add an error message to the model to display to the user
			modelMap.addAttribute("error", "An error occurred while saving the party. Please try again.");
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "error"; // Return to the same view with the error message
		}
	}

}
