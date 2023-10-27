package com.harshi.InventoryAndBilling.helper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.harshi.InventoryAndBilling.entities.Order;
import com.harshi.InventoryAndBilling.entities.OrderLineItem;
import com.harshi.InventoryAndBilling.entities.Product;
import com.harshi.InventoryAndBilling.entities.Warehouse;
/**
 * Utility class for order-related helper methods.
 * This class provides various utility methods for performing calculations and operations related to orders.
 * All methods in this class are static, and it should not be instantiated.
 */
public class OrderHelper {
	
	/**
     * Prevents the instantiation of this utility class by throwing an exception if attempted.
     * This class should not be instantiated, as it only contains static utility methods.
     *
     * @throws IllegalStateException if an attempt is made to create an instance of this class.
     */
	private OrderHelper() {
		throw new IllegalStateException("Helper Class");
	}
	
	/**
	 * Calculates the total quantities for a product based on warehouse quantities.
	 *
	 * @param warehouseQuantities A map of warehouse quantities.
	 * @return The total quantities as a BigDecimal.
	 */
	public static BigDecimal calculateTotalQuantities(Map<Warehouse, Integer> warehouseQuantities) {

		Long totalQuantitiesInWarehouse = 0L;
		for (Entry<Warehouse, Integer> entry : warehouseQuantities.entrySet()) {
			totalQuantitiesInWarehouse += entry.getValue();
		}
		return BigDecimal.valueOf(totalQuantitiesInWarehouse);
	}
	
	/**
	 * Calculates the total quantity of items in the given order.
	 *
	 * @param order The order to calculate the total quantity for.
	 * @return The total quantity of items in the order.
	 */
	public static Integer totalOrderQuantity(Order order) {
	    int totalQuantity = order.getOrderLineItems()
	            .stream()
	            .mapToInt(OrderLineItem::getQuantity)
	            .sum();
	    return totalQuantity;
	}

	/**
	 * Calculates the total price of the order based on its order line items.
	 *
	 * @param order The order to calculate the total price for.
	 * @return The total price of the order.
	 */
	public static BigDecimal totalOrderPrice(Order order) {
	    BigDecimal totalPrice = order.getOrderLineItems()
	            .stream()
	            .map(lineItem -> lineItem.getRate().multiply(BigDecimal.valueOf(lineItem.getQuantity())))
	            .reduce(BigDecimal.ZERO, BigDecimal::add);
	    return totalPrice;
	}

	/**
	 * Calculates the remaining pending price of the order based on its total amount and payments.
	 *
	 * @param order      The order to calculate the pending price for.
	 * @param totalAmount The total order amount.
	 * @return The remaining pending price of the order.
	 */
	public static BigDecimal totalPendingPrice(Order order, BigDecimal totalAmount) {
	    BigDecimal remainingPrice = order.getPayments()
	            .stream()
	            .map(payment -> new BigDecimal(payment.getPayAmount()))
	            .reduce(totalAmount, BigDecimal::subtract);
	    return remainingPrice;
	}

	/**
	 * Updates the product quantities based on order line items.
	 *
	 * @param quantity        The quantity to update.
	 * @param selectedProduct The selected product.
	 * @return A map of warehouse quantities for the order.
	 */
	public static Map<Warehouse, Integer> updateProductQuantities(int quantity, Product selectedProduct) {
	    Map<Warehouse, Integer> warehouseQuantities = new HashMap<>(selectedProduct.getWarehouseQuantities());
	    Map<Warehouse, Integer> orderWarehouseQuantities = new HashMap<>();

	    for (Warehouse warehouse : warehouseQuantities.keySet()) {
	        Integer availableQuantity = warehouseQuantities.get(warehouse);

	        int quantityToDeduct = Math.min(availableQuantity, quantity);

	        if (quantityToDeduct > 0) {
	            warehouseQuantities.put(warehouse, availableQuantity - quantityToDeduct);
	            orderWarehouseQuantities.put(warehouse, quantityToDeduct);
	            quantity -= quantityToDeduct;
	        }

	        if (quantity == 0) {
	            break;
	        }
	    }

	    selectedProduct.setWarehouseQuantities(warehouseQuantities);
	    return orderWarehouseQuantities;
	}

	/**
	 * Converts a LocalDate to a Date with the default system time zone.
	 *
	 * @param localDate The LocalDate to convert.
	 * @return The Date equivalent of the LocalDate.
	 */
	public static Date convertToDate(LocalDate localDate) {
	    return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}
}

