package com.harshi_solution.inventorymate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harshi_solution.inventorymate.entities.User;
import com.harshi_solution.inventorymate.repo.UserRepository;
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

