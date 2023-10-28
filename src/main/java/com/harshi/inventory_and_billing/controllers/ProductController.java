package com.harshi.inventory_and_billing.controllers;

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

import com.harshi.inventory_and_billing.entities.Product;
import com.harshi.inventory_and_billing.entities.Warehouse;
import com.harshi.inventory_and_billing.helper.ProductHelper;
import com.harshi.inventory_and_billing.service.ProductService;
import com.harshi.inventory_and_billing.service.WarehouseService;

/**
 * Controller class for managing product-related operations.
 */
@Controller
public class ProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private WarehouseService warehouseService;

    /**
     * This is an empty product attribute so that the form can use this while rendering.
     *
     * @return A new Product instance.
     */
    @ModelAttribute("product")
    public Product getProduct() {
        return new Product();
    }

    /**
     * Display the list of available products.
     *
     * @param modelMap ModelMap for adding attributes to the view.
     * @return The view name for the product list.
     */
    @RequestMapping("/showProductsList")
    public String showProductsList(ModelMap modelMap) {
        LOGGER.info("Displaying the list of available products");
        // Retrieve available products and add them to the modelMap
        List<Product> productsList = productService.getProductsList();
        modelMap.addAttribute("productsList", productsList);
        return "productView/productsList";
    }

    /**
     * Display the form for adding new products.
     *
     * @param model Model for adding attributes to the view.
     * @return The view name for the add products form.
     */
    @RequestMapping("/showAddProducts")
    public String showAddProducts(Model model) {
        LOGGER.info("Displaying the add products form");
        List<Warehouse> warehouses = warehouseService.getWarehousesList();
        model.addAttribute("warehouses", warehouses);
        return "productView/addProducts";
    }

    /**
     * Handle the addition of new products.
     *
     * @param product   The Product object to be added.
     * @param modelMap  ModelMap for adding attributes to the view.
     * @return The view name for the product list.
     */
    @RequestMapping("/addProducts")
    public String addProducts(@ModelAttribute("product") Product product, ModelMap modelMap) {
        LOGGER.info("Saving a new product");
        // Save the product with warehouse quantities
        product.setBrandName( product.getBrandName().toUpperCase() );
        product.setPType( product.getPType().toUpperCase() );
        Product savedProduct = productService.saveProduct(product);
        List<Product> productsList = productService.getProductsList();
        modelMap.addAttribute("productsList", productsList);
        String msg = "Product saved with ID - " + savedProduct.getProductId();
        modelMap.addAttribute("msg", msg);
        return "productView/productsList";
    }

    /**
     * Display the details of a specific product by productId.
     *
     * @param productId The ID of the product to display.
     * @param model     Model for adding attributes to the view.
     * @return The view name for the product details.
     */
    @GetMapping("/productDetails/{productId}")
    public String showProductDetails(@PathVariable Long productId, Model model) {
        LOGGER.info("Displaying product details for product with ID: {}", productId);
        Product product = productService.getProductById(productId);
        List<Warehouse> warehouses = warehouseService.getWarehousesList();
        Integer totalProductQuantity = ProductHelper.totalProductQuantity(product);
        model.addAttribute("totalProductQuantity", totalProductQuantity);
        model.addAttribute("product", product);
        model.addAttribute("warehouses", warehouses);
        return "productView/productDetails";
    }



	/**
     * Edit product quantities for a specific product by productId.
     *
     * @param productId The ID of the product to edit quantities for.
     * @param model     Model for adding attributes to the view.
     * @return The view name for editing product quantities.
     */
    @GetMapping("/editProductQuantities/{productId}")
    public String editProductQuantities(@PathVariable Long productId, Model model) {
        LOGGER.info("Editing product quantities for product with ID: {}", productId);
        Product product = productService.getProductById(productId);
        List<Warehouse> warehouses = warehouseService.getWarehousesList();
        Integer totalProductQuantity = ProductHelper.totalProductQuantity(product);
        model.addAttribute("totalProductQuantity", totalProductQuantity);
        model.addAttribute("product", product);
        model.addAttribute("warehouses", warehouses);
        return "productView/editProductQuantities";
    }

    /**
     * Update product quantities in the database for a specific product by productId.
     *
     * @param product   The Product object containing updated quantities.
     * @param modelMap  ModelMap for adding attributes to the view.
     * @return The view name for the product list.
     */
    @PostMapping("/updateProductQuantities")
    public String updateProductQuantities(@ModelAttribute("product") Product product, ModelMap modelMap) {
        LOGGER.info("Updating product quantities for product with ID: {}", product.getProductId());
        // Update the product quantities in the database
        Product fetchedProduct = productService.getProductById(product.getProductId());
        if (fetchedProduct.getBrandName().equals(product.getBrandName()) ) {
            fetchedProduct.setWarehouseQuantities(product.getWarehouseQuantities());
            productService.saveProduct(fetchedProduct);
        }
        List<Product> productsList = productService.getProductsList();
        modelMap.addAttribute("productsList", productsList);
        String msg = "Product updated with ID - " + product.getProductId();
        modelMap.addAttribute("msg", msg);
        return "productView/productsList";
    }
}
