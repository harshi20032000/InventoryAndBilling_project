package com.harshi.InventoryAndBilling.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.harshi.InventoryAndBilling.entities.Product;
import com.harshi.InventoryAndBilling.entities.Warehouse;
import com.harshi.InventoryAndBilling.service.ProductService;
import com.harshi.InventoryAndBilling.service.WarehouseService;

@Controller
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private WarehouseService warehouseService;

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

	/**
	 * This ia an empty product attribute so that the form can us this while
	 * rendering
	 */
	@ModelAttribute("product")
	public Product getProduct() {
		return new Product();
	}

	@RequestMapping("/showProductsList")
	public String showProductsList(ModelMap modelMap) {
		LOGGER.info("Inside showAvailableProducts() on DashboardController");
		// Retrieve available products and add them to the modelMap
		List<Product> productsList = productService.showProductsList();
		modelMap.addAttribute("productsList", productsList);
		return "productView/productsList";
	}

	@RequestMapping("/showAddProducts")
	public String showAddProducts(Model model) {
		LOGGER.info("Inside showAddProducts() on DashboardController");
		List<Warehouse> warehouses = warehouseService.showWarehousesList(); // Fetch all warehouses
		model.addAttribute("warehouses", warehouses); // Pass warehouses to the view
		return "productView/addProducts";
	}

	@RequestMapping("/addProducts")
	public String addProducts(@ModelAttribute("product") Product product, ModelMap modelMap) {
		// Save the product with warehouse quantities
		Product savedProduct = productService.saveProduct(product);
		List<Product> productsList = productService.showProductsList();
		modelMap.addAttribute("productsList", productsList);
		String msg = new String("Product saved with id - " + savedProduct.getProductId());
		modelMap.addAttribute("msg", msg);
		return "productView/productsList";
	}

	@GetMapping("/productDetails/{productId}")
	public String showProductDetails(@PathVariable Long productId, Model model) {
		Product product = productService.getProductById(productId);
		List<Warehouse> warehouses = warehouseService.showWarehousesList(); // Fetch all warehouses
		model.addAttribute("product", product);
		model.addAttribute("warehouses", warehouses); // Pass warehouses to the view
		return "productView/productDetails";
	}

	@GetMapping("/editProductQuantities/{productId}")
	public String editProductQuantities(@PathVariable Long productId, Model model) {
		Product product = productService.getProductById(productId);
		List<Warehouse> warehouses = warehouseService.showWarehousesList(); // Fetch all warehouses
		model.addAttribute("product", product);
		model.addAttribute("warehouses", warehouses); // Pass warehouses to the view
		return "productView/editProductQuantities";
	}

	@PostMapping("/updateProductQuantities")
	public String updateProductQuantities(@ModelAttribute("product") Product product, ModelMap modelMap) {
		// Update the product quantities in the database
		Product fetchedProduct = productService.getProductById(product.getProductId());
		if (fetchedProduct.getProductId() == product.getProductId()) {
			fetchedProduct.setWarehouseQuantities(product.getWarehouseQuantities());
			productService.saveProduct(fetchedProduct);}
		List<Product> productsList = productService.showProductsList();
		modelMap.addAttribute("productsList", productsList);
		String msg = new String("Product updated with id - " + product.getProductId());
		modelMap.addAttribute("msg", msg);
		// Redirect to the product details page to display the updated details
		return "productView/productsList";
	}

}
