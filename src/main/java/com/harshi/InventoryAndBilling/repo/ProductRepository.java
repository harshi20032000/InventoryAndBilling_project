package com.harshi.InventoryAndBilling.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harshi.InventoryAndBilling.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
