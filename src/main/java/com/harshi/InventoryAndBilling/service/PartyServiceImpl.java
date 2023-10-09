package com.harshi.InventoryAndBilling.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harshi.InventoryAndBilling.entities.Party;
import com.harshi.InventoryAndBilling.repo.PartyRepository;

@Service
public class PartyServiceImpl implements PartyService {

	@Autowired
	private PartyRepository partyRepository;

	@Override
	public Party saveParty(Party party) {

		return partyRepository.save(party);
	}

	@Override
	public List<Party> getPartyList() {

		return partyRepository.findAll();
	}

	@Override
	public Party getPartyById(Long partyId) {
		return partyRepository.findById(partyId).orElse(null);
	}

}
