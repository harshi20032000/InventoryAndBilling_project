package com.harshi.InventoryAndBilling.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
    @JoinColumn(name = "transport_and_built_number_id")
    private TransportAndBuiltNumber transportAndBuiltNumber;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderLineItem> orderLineItems = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "payment_id")
	private Payment payment;

	// Constructors, getters, and setters

	public Order() {
		// Default constructor
	}

	public Order(Date orderDate, Reps reps, Party party, TransportAndBuiltNumber transportAndBuiltNumber) {
        this.orderDate = orderDate;
        this.reps = reps;
        this.party = party;
        this.transportAndBuiltNumber = transportAndBuiltNumber;
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

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	// Other methods if needed

	public List<OrderLineItem> getOrderLineItems() {
		return orderLineItems;
	}

	public void setOrderLineItems(List<OrderLineItem> orderLineItems) {
		this.orderLineItems = orderLineItems;
	}

	public TransportAndBuiltNumber getTransportAndBuiltNumber() {
		return transportAndBuiltNumber;
	}

	public void setTransportAndBuiltNumber(TransportAndBuiltNumber transportAndBuiltNumber) {
		this.transportAndBuiltNumber = transportAndBuiltNumber;
	}

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", orderDate=" + orderDate + ", reps=" + reps + ", party=" + party
				+ ", transport=" + transportAndBuiltNumber + ", orderLineItems=" + orderLineItems + ", payment=" + payment + "]";
	}
	
	 public void addLineItem(OrderLineItem lineItem) {
	        // Set the order for the line item
	        lineItem.setOrder(this);
	        
	        // Add the line item to the list of line items
	        orderLineItems.add(lineItem);
	    }

	    public void removeLineItem(OrderLineItem lineItem) {
	        // Remove the line item from the list of line items
	    	orderLineItems.remove(lineItem);
	        
	        // Set the order for the line item to null
	        if (lineItem != null) {
	            lineItem.setOrder(null);
	        }
	    }



}
