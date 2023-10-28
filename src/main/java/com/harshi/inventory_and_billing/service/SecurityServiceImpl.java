package com.harshi.inventory_and_billing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harshi.inventory_and_billing.repo.UserRepository;
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

