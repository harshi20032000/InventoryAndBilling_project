package com.harshi.InventoryAndBilling.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "party")
public class Party {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long partyId;

    private String partyName;
    private String partyLocation;

    @OneToMany(mappedBy = "party")
    private Set<Payment> payments = new HashSet<>();

    // Constructors, getters, and setters

    public Party() {
        // Default constructor
    }

    public Party(String partyName, String partyLocation) {
        this.partyName = partyName;
        this.partyLocation = partyLocation;
    }

    // Getters and setters

    public Long getPartyId() {
        return partyId;
    }

    public void setPartyId(Long partyId) {
        this.partyId = partyId;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getPartyLocation() {
        return partyLocation;
    }

    public void setPartyLocation(String partyLocation) {
        this.partyLocation = partyLocation;
    }

    public Set<Payment> getPayments() {
        return payments;
    }

    // Other methods if needed

    @Override
    public String toString() {
        return "Party{" +
                "partyId=" + partyId +
                ", partyName='" + partyName + '\'' +
                ", partyLocation='" + partyLocation + '\'' +
                '}';
    }
}
