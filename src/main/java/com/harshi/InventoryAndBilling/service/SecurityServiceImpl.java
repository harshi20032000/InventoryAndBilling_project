package com.harshi.InventoryAndBilling.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harshi.InventoryAndBilling.repo.UserRepository;
@Service
public class SecurityServiceImpl implements SecurityService {

	@Autowired
	UserRepository userRepository;

	@Override
	public boolean login(String username, String password) {
		
		boolean isAuthenticated = userRepository.findByEmail(username).getPassword().equals(password);
		
		return isAuthenticated;
	}


}

