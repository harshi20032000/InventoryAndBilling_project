package com.harshi_solution.inventorymate.service;

import java.util.List;

import com.harshi_solution.inventorymate.entities.Product;

public interface ProductService {

	List<Product> getProductsList();

	Product saveProduct(Product product);

	Product getProductById(Long productId);

}
