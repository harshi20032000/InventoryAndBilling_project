package com.harshi_solution.inventorymate.service;

import java.util.List;

import com.harshi_solution.inventorymate.entities.Transport;

public interface TransportService {
	
	public Transport saveTransport(Transport transport);
	
	public List<Transport> getTransportList();

	public Transport getTransportById(Long selectedTransportId);

}
