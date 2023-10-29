package com.harshi.inventory_and_billing.entities;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "warehouse")
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wareId;
    
    @Column(unique = true)
    @NotBlank(message = "wareName is mandatory")
    @Length(min=5, max=15, message="lastName should be min 5 and max 15")
    private String wareName;
    
    @Column(unique = true)
    @NotBlank(message = "wareCode is mandatory")
    @Length(min=3, max=5, message="wareCode should be min 3 and max 5")
    private String wareCode; 
    
    @ElementCollection
	@CollectionTable(name = "product_warehouse_quantity", joinColumns = @JoinColumn(name = "warehouse_id"))
	@MapKeyJoinColumn(name = "product_id")
	@Column(name = "quantity")
	private Map<Product, Integer> productQuantities = new HashMap<>();

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

    // Other methods if needed

    public Map<Product, Integer> getProductQuantities() {
		return productQuantities;
	}

	public void setProductQuantities(Map<Product, Integer> productQuantities) {
		this.productQuantities = productQuantities;
	}

	@Override
    public String toString() {
        return "Warehouse{" +
                "wareId=" + wareId +
                ", wareName='" + wareName + '\'' +
                ", wareCode='" + wareCode + '\'' + // Include wareCode in toString
                '}';
    }
}
