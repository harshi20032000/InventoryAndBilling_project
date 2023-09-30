package com.harshi.InventoryAndBilling.entities;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long payId;

    @Temporal(TemporalType.DATE)
    private Date payDate;

    private Double payAmount;
    private String payMode;
    private String payType;

    @ManyToOne
    @JoinColumn(name = "party_id")
    private Party party;

    // Constructors, getters, and setters

    public Payment() {
        // Default constructor
    }

    public Payment(Date payDate, Double payAmount, String payMode, String payType) {
        this.payDate = payDate;
        this.payAmount = payAmount;
        this.payMode = payMode;
        this.payType = payType;
    }

    // Getters and setters

    public Long getPayId() {
        return payId;
    }

    public void setPayId(Long payId) {
        this.payId = payId;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public Double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(Double payAmount) {
        this.payAmount = payAmount;
    }

    public String getPayMode() {
        return payMode;
    }

    public void setPayMode(String payMode) {
        this.payMode = payMode;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    // Other methods if needed

    @Override
    public String toString() {
        return "Payment{" +
                "payId=" + payId +
                ", payDate=" + payDate +
                ", payAmount=" + payAmount +
                ", payMode='" + payMode + '\'' +
                ", payType='" + payType + '\'' +
                '}';
    }
}

