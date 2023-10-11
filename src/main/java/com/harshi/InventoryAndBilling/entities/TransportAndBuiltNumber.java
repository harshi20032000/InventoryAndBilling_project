package com.harshi.InventoryAndBilling.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "transport_and_built_number")
public class TransportAndBuiltNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "transport_id")
    private Transport transport;

    private String builtNumber;

    // Constructors, getters, and setters

    public TransportAndBuiltNumber() {
        // Default constructor
    }

    public TransportAndBuiltNumber(Transport transport, String builtNumber) {
        this.transport = transport;
        this.builtNumber = builtNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Transport getTransport() {
        return transport;
    }

    public void setTransport(Transport transport) {
        this.transport = transport;
    }

    public String getBuiltNumber() {
        return builtNumber;
    }

    public void setBuiltNumber(String builtNumber) {
        this.builtNumber = builtNumber;
    }
    
    @Override
    public String toString() {
        return "TransportAndBuiltNumber [id=" + id + ", transport=" + transport + ", builtNumber=" + builtNumber + "]";
    }

}


