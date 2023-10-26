package com.harshi.InventoryAndBilling.entities;

public enum OrderStatus {
    ORDER_PLACED("Order Placed"),
    BUILTY_DETAILS_UPDATED("BuiltyDetails Updated"),
    PAYMENT_RECEIVED("Payment Received, Remaining Payment - $400"),
    FULLY_PAID("Fully Paid"),
    ORDER_DELIVERED("Order Delivered");

    private String displayName;

    OrderStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

