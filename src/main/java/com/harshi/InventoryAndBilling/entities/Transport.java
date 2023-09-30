package com.harshi.InventoryAndBilling.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "transport")
public class Transport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transportId;

    private String transportName;

    // Constructors, getters, and setters

    public Transport() {
        // Default constructor
    }

    public Transport(String transportName) {
        this.transportName = transportName;
    }

    // Getters and setters

    public Long getTransportId() {
        return transportId;
    }

    public void setTransportId(Long transportId) {
        this.transportId = transportId;
    }

    public String getTransportName() {
        return transportName;
    }

    public void setTransportName(String transportName) {
        this.transportName = transportName;
    }

    // Other methods if needed

    @Override
    public String toString() {
        return "Transport{" +
                "transportId=" + transportId +
                ", transportName='" + transportName + '\'' +
                '}';
    }
}
