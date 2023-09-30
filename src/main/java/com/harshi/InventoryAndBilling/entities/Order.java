package com.harshi.InventoryAndBilling.entities;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Temporal(TemporalType.DATE)
    private Date orderDate;

    @ManyToOne
    @JoinColumn(name = "rep_id")
    private Reps reps;

    @ManyToOne
    @JoinColumn(name = "party_id")
    private Party party;

    @ManyToOne
    @JoinColumn(name = "transport_id")
    private Transport transport;

    @ElementCollection
    @CollectionTable(name = "order_quantity_details")
    @MapKeyJoinColumn(name = "product_id")
    @Column(name = "quantity")
    private Map<Product, Integer> quantityDetails = new HashMap<>();

    @ElementCollection
    @CollectionTable(name = "order_price_details")
    @MapKeyJoinColumn(name = "product_id")
    @Column(name = "price")
    private Map<Product, Double> priceDetails = new HashMap<>();

    @ManyToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

    // Constructors, getters, and setters

    public Order() {
        // Default constructor
    }

    public Order(Date orderDate, Reps reps, Party party, Transport transport) {
        this.orderDate = orderDate;
        this.reps = reps;
        this.party = party;
        this.transport = transport;
    }

    // Getters and setters

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Reps getReps() {
        return reps;
    }

    public void setReps(Reps reps) {
        this.reps = reps;
    }

    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    public Transport getTransport() {
        return transport;
    }

    public void setTransport(Transport transport) {
        this.transport = transport;
    }

    public Map<Product, Integer> getQuantityDetails() {
        return quantityDetails;
    }

    public void setQuantityDetails(Map<Product, Integer> quantityDetails) {
        this.quantityDetails = quantityDetails;
    }

    public Map<Product, Double> getPriceDetails() {
        return priceDetails;
    }

    public void setPriceDetails(Map<Product, Double> priceDetails) {
        this.priceDetails = priceDetails;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    // Other methods if needed

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", orderDate=" + orderDate +
                ", reps=" + reps +
                ", party=" + party +
                ", transport=" + transport +
                '}';
    }
}
