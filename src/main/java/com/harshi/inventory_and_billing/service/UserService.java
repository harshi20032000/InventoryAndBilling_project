package com.harshi.inventory_and_billing.service;

import com.harshi.inventory_and_billing.entities.User;

public interface UserService {
	boolean login(String username, String password);

	User saveUser(User user);

}
