package com.harshi.inventory_and_billing.helper;

import java.util.Map;

import com.harshi.inventory_and_billing.entities.Product;
import com.harshi.inventory_and_billing.entities.Warehouse;

/**
 * Utility class for product-related helper methods.
 * This class provides various utility methods for performing calculations and operations related to products.
 * All methods in this class are static, and it should not be instantiated.
 */
public class ProductHelper {

	/**
     * Prevents the instantiation of this utility class by throwing an exception if attempted.
     * This class should not be instantiated, as it only contains static utility methods.
     *
     * @throws IllegalStateException if an attempt is made to create an instance of this class.
     */
	private ProductHelper() {
		throw new IllegalStateException("Helper Class");
	}
	

	/**
	 * Calculates the total quantity of a specific product by summing the quantities
	 * of that product in each warehouse.
	 *
	 * @param product The product for which you want to find the total quantity.
	 * @return The sum of quantities of the product across all warehouses.
	 */
    public static Integer totalProductQuantity(Product product) {
        Integer totalProductQuantity = 0;
        Map<Warehouse, Integer> warehouseQuantities = product.getWarehouseQuantities();

        for (Integer quantity : warehouseQuantities.values()) {
            totalProductQuantity += quantity;
        }

        return totalProductQuantity;
    }
}
