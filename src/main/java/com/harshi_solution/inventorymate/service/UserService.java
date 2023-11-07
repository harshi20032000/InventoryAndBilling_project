package com.harshi_solution.inventorymate.service;

import com.harshi_solution.inventorymate.entities.User;

public interface UserService {
	boolean login(String username, String password);

	User saveUser(User user);

}
