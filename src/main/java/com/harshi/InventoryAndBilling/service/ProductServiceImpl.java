package com.harshi.InventoryAndBilling.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harshi.InventoryAndBilling.entities.Product;
import com.harshi.InventoryAndBilling.repo.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;


	@Override
	public List<Product> showProductsList() {
		return productRepository.findAll();
	}


	@Override
	public Product saveProduct(Product product) {
		return productRepository.save(product);
	}


	@Override
	public Product getProductById(Long productId) {
		return productRepository.findById(productId).get();
	}

}
