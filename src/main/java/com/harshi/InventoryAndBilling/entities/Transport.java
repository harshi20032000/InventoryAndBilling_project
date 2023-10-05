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
    private String builtNo;
    private String contactDetails;

    // Constructors, getters, and setters

    public Transport() {
        // Default constructor
    }

    public Transport(String transportName, String builtNo, String contactDetails) {
        this.transportName = transportName;
        this.builtNo = builtNo;
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

    public String getBuiltNo() {
        return builtNo;
    }

    public void setBuiltNo(String builtNo) {
        this.builtNo = builtNo;
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
                ", transportName='" + transportName + '\'' +
                ", builtNo='" + builtNo + '\'' +
                ", contactDetails='" + contactDetails + '\'' +
                '}';
    }
}
