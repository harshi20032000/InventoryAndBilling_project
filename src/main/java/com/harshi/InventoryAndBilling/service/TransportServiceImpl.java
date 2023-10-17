package com.harshi.InventoryAndBilling.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harshi.InventoryAndBilling.entities.Transport;
import com.harshi.InventoryAndBilling.repo.TransportRepository;

@Service
public class TransportServiceImpl implements TransportService {

	@Autowired
	private TransportRepository transportRepository;

	@Override
	public Transport saveTransport(Transport transport) {

		return transportRepository.save(transport);
	}

	@Override
	public List<Transport> getTransportList() {

		return transportRepository.findAll();
	}

	@Override
	public Transport getTransportById(Long selectedTransportId) {

		return transportRepository.findById(selectedTransportId).orElse(null);
	}

}
