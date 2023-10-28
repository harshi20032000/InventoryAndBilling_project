package com.harshi.inventory_and_billing.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "reps")
public class Reps {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long repId;

    private String repName;
    private String repLocation;

    // Constructors, getters, and setters

    public Reps() {
        // Default constructor
    }

    public Reps(String repName, String repLocation) {
        this.repName = repName;
        this.repLocation = repLocation;
    }

    // Getters and setters

    public Long getRepId() {
        return repId;
    }

    public void setRepId(Long repId) {
        this.repId = repId;
    }

    public String getRepName() {
        return repName;
    }

    public void setRepName(String repName) {
        this.repName = repName;
    }

    public String getRepLocation() {
        return repLocation;
    }

    public void setRepLocation(String repLocation) {
        this.repLocation = repLocation;
    }

    // Other methods if needed

    @Override
    public String toString() {
        return "Reps{" +
                "repId=" + repId +
                ", repName='" + repName + '\'' +
                ", repLocation='" + repLocation + '\'' +
                '}';
    }
}

