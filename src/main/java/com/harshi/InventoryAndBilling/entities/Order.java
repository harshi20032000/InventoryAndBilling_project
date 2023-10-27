package com.harshi.InventoryAndBilling.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
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
	private LocalDate orderDate;

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

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Payment> payments = new ArrayList<>();

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderStatusHistory> statusHistory = new ArrayList<>();
	
	private BigDecimal totalBillAmount; 

    private BigDecimal remainingBillAmount; 

	// Constructors, getters, and setters

	public Order() {
		// Default constructor
	}

	public Order(LocalDate orderDate, Reps reps, Party party, TransportAndBuiltNumber transportAndBuiltNumber) {
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

	public LocalDate getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDate orderDate) {
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

	public List<Payment> getPayments() {
		return payments;
	}

	public void setPayments(List<Payment> payments) {
		this.payments = payments;
	}

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", orderDate=" + orderDate + ", reps=" + reps + ", party=" + party
				+ ", transport=" + transportAndBuiltNumber + ", orderLineItems=" + orderLineItems + "]";
	}

	public void addPayment(Payment payment) {
		if (payments == null) {
			payments = new ArrayList<>();
		}
		payments.add(payment);
		payment.setOrder(this);
	}

	public List<OrderStatusHistory> getStatusHistory() {
		return statusHistory;
	}

	public void setStatusHistory(List<OrderStatusHistory> statusHistory) {
		this.statusHistory = statusHistory;
	}

	public void addStatusChange(OrderStatusHistory change) {
		statusHistory.add(change);
	}

	public BigDecimal getTotalBillAmount() {
		return totalBillAmount;
	}

	public void setTotalBillAmount(BigDecimal totalBillAmount) {
		this.totalBillAmount = totalBillAmount;
	}

	public BigDecimal getRemainingBillAmount() {
		return remainingBillAmount;
	}

	public void setRemainingBillAmount(BigDecimal remainingBillAmount) {
		this.remainingBillAmount = remainingBillAmount;
	}

}
