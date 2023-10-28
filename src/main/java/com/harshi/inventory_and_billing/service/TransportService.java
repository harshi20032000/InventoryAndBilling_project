package com.harshi.inventory_and_billing.service;

import java.util.List;

import com.harshi.inventory_and_billing.entities.Transport;

public interface TransportService {
	
	public Transport saveTransport(Transport transport);
	
	public List<Transport> getTransportList();

	public Transport getTransportById(Long selectedTransportId);

}
