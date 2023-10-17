package com.harshi.InventoryAndBilling.service;

import java.util.List;

import com.harshi.InventoryAndBilling.entities.Transport;

public interface TransportService {
	
	public Transport saveTransport(Transport transport);
	
	public List<Transport> getTransportList();

	public Transport getTransportById(Long selectedTransportId);

}
