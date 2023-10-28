package com.harshi.inventory_and_billing.service;

import java.util.List;

import com.harshi.inventory_and_billing.entities.Product;

public interface ProductService {

	List<Product> getProductsList();

	Product saveProduct(Product product);

	Product getProductById(Long productId);

}
