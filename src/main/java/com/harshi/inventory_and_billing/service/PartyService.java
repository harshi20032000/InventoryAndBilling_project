package com.harshi.inventory_and_billing.service;

import java.util.List;

import com.harshi.inventory_and_billing.entities.Order;
import com.harshi.inventory_and_billing.entities.Party;

public interface PartyService {
	
	public Party saveParty(Party party);
	
	public List<Party> getPartyList();

	public Party getPartyById(Long partyId);

	public List<Order> getOrderListByParty(Long partyId);
}
