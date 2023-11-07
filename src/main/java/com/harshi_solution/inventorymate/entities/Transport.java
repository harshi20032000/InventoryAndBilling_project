package com.harshi_solution.inventorymate.entities;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "transport")
public class Transport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transportId;

    @Column(unique = true)
    @Length(min=3, max=30, message="transportName should be min 3 and max 30")
    @NotBlank(message="transportName is mandatory")
    private String transportName;
    
    private String contactDetails;

    // Constructors, getters, and setters

    public Transport() {
        // Default constructor
    }

    public Transport(String transportName, String contactDetails) {
        this.transportName = transportName;
        this.contactDetails = contactDetails;
    }

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

    public String getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(String contactDetails) {
        this.contactDetails = contactDetails;
    }

    @Override
    public String toString() {
        return "Transport{" +
                "transportId=" + transportId +
                ", transportName='" + transportName + '\''+ '\'' +
                ", contactDetails='" + contactDetails + '\'' +
                '}';
    }
}
