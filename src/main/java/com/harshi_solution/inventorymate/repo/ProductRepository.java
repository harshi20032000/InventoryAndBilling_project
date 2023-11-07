package com.harshi_solution.inventorymate.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harshi_solution.inventorymate.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
