package com.harshi.InventoryAndBilling.service;

import java.util.List;

import com.harshi.InventoryAndBilling.entities.Party;

public interface PartyService {
	
	public Party saveParty(Party party);
	
	public List<Party> getPartyList();

	public Party getPartyById(Long partyId);
}
