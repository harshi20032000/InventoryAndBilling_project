package com.harshi.inventory_and_billing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harshi.inventory_and_billing.entities.User;
import com.harshi.inventory_and_billing.repo.UserRepository;
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Override
	public boolean login(String username, String password) {
		return userRepository.findByEmail(username).getPassword().equals(password);
	}

	@Override
	public User saveUser(User user) {
		// Convert user data to uppercase
        user.setEmail(user.getEmail().toUpperCase());
        user.setLastName(user.getLastName().toUpperCase());
        user.setFirstName(user.getFirstName().toUpperCase());
        user.setPassword(user.getPassword().toUpperCase());
        // Save the user
        return userRepository.save(user);
	}

}

