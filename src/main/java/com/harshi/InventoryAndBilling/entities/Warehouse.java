package com.harshi.InventoryAndBilling.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "warehouse")
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wareId;
    @Column(unique = true)
    private String wareName;
    
    private String wareCode; // Add the wareCode property

    @ManyToMany()
    @JoinTable(
            name = "products_warehouses",
            joinColumns = @JoinColumn(name = "warehouse_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
        )
    private Set<Product> products = new HashSet<>();

    // Constructors, getters, and setters

    public Warehouse() {
        // Default constructor
    }

    public Warehouse(String wareName, String wareCode) { // Update constructor
        this.wareName = wareName;
        this.wareCode = wareCode;
    }

    // Getters and setters

    public Long getWareId() {
        return wareId;
    }

    public void setWareId(Long wareId) {
        this.wareId = wareId;
    }

    public String getWareName() {
        return wareName;
    }

    public void setWareName(String wareName) {
        this.wareName = wareName;
    }

    public String getWareCode() { // Add getter for wareCode
        return wareCode;
    }

    public void setWareCode(String wareCode) { // Add setter for wareCode
        this.wareCode = wareCode;
    }

    public Set<Product> getProducts() {
        return products;
    }

    // Other methods if needed

    @Override
    public String toString() {
        return "Warehouse{" +
                "wareId=" + wareId +
                ", wareName='" + wareName + '\'' +
                ", wareCode='" + wareCode + '\'' + // Include wareCode in toString
                '}';
    }
}
