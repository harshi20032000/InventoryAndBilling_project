package com.harshi_solution.inventorymate.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harshi_solution.inventorymate.entities.Order;
import com.harshi_solution.inventorymate.entities.Party;
import com.harshi_solution.inventorymate.repo.OrderRepository;
import com.harshi_solution.inventorymate.repo.PartyRepository;

@Service
public class PartyServiceImpl implements PartyService {

	@Autowired
	private PartyRepository partyRepository;

	@Autowired
	private OrderRepository orderRepository;

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

	@Override
	public List<Order> getOrderListByParty(Long partyId) {
		Party party = partyRepository.findById(partyId).orElse(null);
		return orderRepository.findAllByParty(party);
	}

}
