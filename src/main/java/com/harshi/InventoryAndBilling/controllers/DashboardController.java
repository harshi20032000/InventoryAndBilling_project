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
import org.springframework.web.bind.annotation.RequestMapping;

import com.harshi.InventoryAndBilling.entities.Product;
import com.harshi.InventoryAndBilling.service.PaymentService;
import com.harshi.InventoryAndBilling.service.ProductService;

@Controller
public class DashboardController {

	@Autowired
	private PaymentService paymentService;

	@Autowired
	private ProductService productService;


	private static final Logger LOGGER = LoggerFactory.getLogger(DashboardController.class);

	/**
	 * This ia an empty product attribute so that the form can us this while
	 * rendering
	 */
	@ModelAttribute("product")
	public Product getProduct() {
		return new Product();
	}

	@RequestMapping("/showTotalOutstanding")
	public String showTotalOutstanding(ModelMap modelMap) {
		LOGGER.info("Inside getTotalOutstanding() on DashboardController");
		modelMap.addAttribute("totalOutstanding", paymentService.getTotalOutstanding());
		return "dashboard/totalOutstanding";
	}

	@RequestMapping(value = "/showLanding")
	public String login(ModelMap modelMap) {
		LOGGER.info("Redirecting landing.html");
		modelMap.addAttribute("msg", ".....On Home Page.....");
		return "dashboard/landing";
	}

	@RequestMapping("/showProductsList")
	public String showProductsList(ModelMap modelMap) {
		LOGGER.info("Inside showAvailableProducts() on DashboardController");
		// Retrieve available products and add them to the modelMap
		List<Product> productsList = productService.showProductsList();
		modelMap.addAttribute("productsList", productsList);
		return "dashboard/productsList";
	}

	@RequestMapping("/showAddProducts")
	public String showAddProducts() {
		LOGGER.info("Inside showAddProducts() on DashboardController");
		return "dashboard/addProducts";
	}

	@RequestMapping("/addProducts")
	public String addProducts(@ModelAttribute("product") Product product, ModelMap modelMap) {
		Product savedProduct = productService.saveProduct(product);
		List<Product> productsList = productService.showProductsList();
		modelMap.addAttribute("productsList", productsList);
		String msg = new String("Product saved with id - " + savedProduct.getProductId());
		modelMap.addAttribute("msg", msg);
		// emailUtil.sendEmail("learningpandit@gmail.com", msg,
		// savedProduct.toString());
		return "dashboard/productsList";
	}
	
    @GetMapping("/productDetails/{productId}")
    public String showProductDetails(@PathVariable Long productId, Model model) {
        Product product = productService.getProductById(productId);
        model.addAttribute("product", product);
        return "dashboard/productDetails";
    }

}
