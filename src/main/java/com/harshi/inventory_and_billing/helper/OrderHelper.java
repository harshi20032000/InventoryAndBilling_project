package com.harshi.inventory_and_billing.helper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import com.harshi.inventory_and_billing.entities.Order;
import com.harshi.inventory_and_billing.entities.OrderLineItem;
import com.harshi.inventory_and_billing.entities.Payment;
import com.harshi.inventory_and_billing.entities.Product;
import com.harshi.inventory_and_billing.entities.Warehouse;

/**
 * Utility class for order-related helper methods. This class provides various
 * utility methods for performing calculations and operations related to orders.
 * All methods in this class are static, and it should not be instantiated.
 */
public class OrderHelper {

	/**
	 * Prevents the instantiation of this utility class by throwing an exception if
	 * attempted. This class should not be instantiated, as it only contains static
	 * utility methods.
	 *
	 * @throws IllegalStateException if an attempt is made to create an instance of
	 *                               this class.
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
		return order.getOrderLineItems().stream().mapToInt(OrderLineItem::getQuantity).sum();
	}

	/**
	 * Calculates the total price of the order based on its order line items.
	 *
	 * @param order The order to calculate the total price for.
	 * @return The total price of the order.
	 */
	public static BigDecimal totalOrderPrice(Order order) {
		return order.getOrderLineItems().stream()
				.map(lineItem -> lineItem.getRate().multiply(BigDecimal.valueOf(lineItem.getQuantity())))
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	/**
	 * Calculates the remaining pending price of the order based on its total amount
	 * and payments.
	 *
	 * @param order       The order to calculate the pending price for.
	 * @param totalAmount The total order amount.
	 * @return The remaining pending price of the order.
	 */
	public static BigDecimal totalPendingPrice(Order order, BigDecimal totalAmount) {
		return order.getPayments().stream().map(payment -> new BigDecimal(payment.getPayAmount())).reduce(totalAmount,
				BigDecimal::subtract);
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
	 * Updates the product quantities based on order line items, giving preference to warehouses used in the last order.
	 *
	 * @param quantity  The quantity to update.
	 * @param selectedProduct  The selected product.
	 * @param order The current order from which to fetch the last order quantities.
	 * @return A map of warehouse quantities for the order.
	 */
	public static Map<Warehouse, Integer> updateProductQuantities(int quantity, Product selectedProduct, Order order) {
	    Map<Warehouse, Integer> warehouseQuantities = new HashMap<>(selectedProduct.getWarehouseQuantities());
	    Map<Warehouse, Integer> orderWarehouseQuantities = new HashMap<>();
	    List<Warehouse> warehouseList = new ArrayList<>(); // Declare the list here

	    if (order != null && order.getOrderLineItems() != null && !order.getOrderLineItems().isEmpty()) {
	        // Calculate List of warehouses from the first orderlineItems of the selected order if present.
	        // These warehouseList will be given preference for the current orderlineitems.
	        OrderLineItem orderLineItem = order.getOrderLineItems().get(0);
	        Map<Warehouse, Integer> previousOrderLineWarehouseQuantities = orderLineItem.getOrderWarehouseQuantities();
	        warehouseList.addAll(previousOrderLineWarehouseQuantities.keySet()); // Add warehouses to the list
	        //these warehouseList will be given preference for the current orderlineitems. 
	    }

	    // Logic for preference to warehouses used in the last order
	    for (Warehouse preferredWarehouse : warehouseList) {
	        int availableQuantity = warehouseQuantities.get(preferredWarehouse);

	        int quantityToDeduct = Math.min(availableQuantity, quantity);

	        if (quantityToDeduct > 0) {
	            warehouseQuantities.put(preferredWarehouse, availableQuantity - quantityToDeduct);
	            orderWarehouseQuantities.put(preferredWarehouse, quantityToDeduct);
	            quantity -= quantityToDeduct;
	        }

	        if (quantity == 0) {
	            break; // We've fulfilled the required quantity
	        }
	    }

	    // If quantity is still remaining, deduct from other warehouses
	    for (Warehouse warehouse : warehouseQuantities.keySet()) {
	        int availableQuantity = warehouseQuantities.get(warehouse);

	        int quantityToDeduct = Math.min(availableQuantity, quantity);

	        if (quantityToDeduct > 0) {
	            warehouseQuantities.put(warehouse, availableQuantity - quantityToDeduct);
	            orderWarehouseQuantities.put(warehouse, quantityToDeduct);
	            quantity -= quantityToDeduct;
	        }

	        if (quantity == 0) {
	            break; // We've fulfilled the required quantity
	        }
	    }

	    // Update the product's warehouse quantities with the changes
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

	/**
	 * Calculates the total price of a list of orders based on their order line
	 * items.
	 *
	 * @param orderList The list of orders to calculate the total price for.
	 * @return The total price of the orders in the list.
	 */
	public static BigDecimal totalOrderPrice(List<Order> orderList) {
		return orderList.stream().flatMap(order -> order.getOrderLineItems().stream())
				.map(lineItem -> lineItem.getRate().multiply(BigDecimal.valueOf(lineItem.getQuantity())))
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	/**
	 * Calculates the total pending price for a list of orders based on their total
	 * amounts and payments.
	 *
	 * @param orderList The list of orders to calculate the pending price for.
	 * @return The total pending price for the orders in the list.
	 */
	public static BigDecimal totalPendingPrice(List<Order> orderList) {

		return orderList.stream().map(order -> {
			// Use Optional to handle null values
			Optional<BigDecimal> totalBillAmountOptional = Optional.ofNullable(order.getTotalBillAmount());
			Optional<List<Payment>> paymentsOptional = Optional.ofNullable(order.getPayments());

			return paymentsOptional.orElse(List.of()).stream().map(payment -> new BigDecimal(payment.getPayAmount()))
					.reduce(totalBillAmountOptional.orElse(BigDecimal.ZERO), BigDecimal::subtract);
		}).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

}
