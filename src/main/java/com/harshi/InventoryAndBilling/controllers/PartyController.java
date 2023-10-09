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

import com.harshi.InventoryAndBilling.entities.Party;
import com.harshi.InventoryAndBilling.service.PartyService;

@Controller
public class PartyController {

    @Autowired
    private PartyService partyService;

    // a method to show the party list
    @RequestMapping("/showPartyList")
    public String showPartyList(ModelMap modelMap) {
        List<Party> partyList = partyService.getPartyList();
        modelMap.addAttribute("partyList", partyList);
        return "partyView/partyList";
    }

    // a method to show party details by partyId
    @GetMapping("/partyDetails/{partyId}")
    public String showPartyDetails(@PathVariable Long partyId, Model model) {
        Party party = partyService.getPartyById(partyId);
        model.addAttribute("party", party);
        return "partyView/partyDetails";
    }

    // a method to display the add party form
    @RequestMapping("/showAddParty")
    public String showAddParty(Model model) {
        model.addAttribute("party", new Party());
        return "partyView/addParty";
    }

    // a method to handle party addition
    @PostMapping("/addParty")
    public String addParty(@ModelAttribute("party") Party party, ModelMap modelMap) {
        Party savedParty = partyService.saveParty(party);
        List<Party> partyList = partyService.getPartyList();
        modelMap.addAttribute("partyList", partyList);
        String msg = "Party saved with ID - " + savedParty.getPartyId();
        modelMap.addAttribute("msg", msg);
        return "partyView/partyList";
    }
}
