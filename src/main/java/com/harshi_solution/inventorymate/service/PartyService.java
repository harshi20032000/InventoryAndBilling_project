package com.harshi_solution.inventorymate.service;

import java.util.List;

import com.harshi_solution.inventorymate.entities.Order;
import com.harshi_solution.inventorymate.entities.Party;

public interface PartyService {
	
	public Party saveParty(Party party);
	
	public List<Party> getPartyList();

	public Party getPartyById(Long partyId);

	public List<Order> getOrderListByParty(Long partyId);
}
