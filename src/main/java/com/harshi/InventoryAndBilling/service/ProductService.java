package com.harshi.InventoryAndBilling.service;

import java.util.List;

import com.harshi.InventoryAndBilling.entities.Product;

public interface ProductService {

	List<Product> showProductsList();

	Product saveProduct(Product product);

	Product getProductById(Long productId);

}