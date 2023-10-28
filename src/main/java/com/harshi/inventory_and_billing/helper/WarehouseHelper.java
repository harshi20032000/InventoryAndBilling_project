package com.harshi.inventory_and_billing.helper;

import java.util.Map;

import com.harshi.inventory_and_billing.entities.Product;
import com.harshi.inventory_and_billing.entities.Warehouse;
/**
 * Utility class for warehouse-related helper methods.
 * This class provides various utility methods for performing calculations and operations related to warehouses.
 * All methods in this class are static, and it should not be instantiated.
 */
public class WarehouseHelper {

	/**
     * Prevents the instantiation of this utility class by throwing an exception if attempted.
     * This class should not be instantiated, as it only contains static utility methods.
     *
     * @throws IllegalStateException if an attempt is made to create an instance of this class.
     */
	private WarehouseHelper() {
		throw new IllegalStateException("Helper Class");
	}

	/**
	 * Calculates the total quantity of all products in a specific warehouse by summing
	 * the quantities of each product it contains.
	 *
	 * @param warehouse The warehouse for which you want to find the total product quantity.
	 * @return The sum of quantities of all products in that warehouse.
	 */
    public static Integer totalProductQuantity(Warehouse warehouse) {
    	Integer totalProductQuantity = 0;
        Map<Product, Integer> productQuantities = warehouse.getProductQuantities();

        for (Integer quantity : productQuantities.values()) {
            totalProductQuantity += quantity;
        }

        return totalProductQuantity;
	}

}
