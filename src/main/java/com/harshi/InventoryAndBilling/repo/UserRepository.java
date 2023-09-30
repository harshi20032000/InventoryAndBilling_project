package com.harshi.InventoryAndBilling.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harshi.InventoryAndBilling.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);

}
