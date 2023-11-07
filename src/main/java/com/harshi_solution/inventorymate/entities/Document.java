package com.harshi_solution.inventorymate.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

/**
 * The Document entity represents a file or document that can be stored in the database.
 */
@Entity
@Table(name = "doc")
public class Document {
    
    /**
     * The unique identifier for the document.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long docId;

    /**
     * The name of the document.
     */
    private String name;

    /**
     * The binary data of the document, stored as a byte array.
     */
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] data;

    /**
     * Get the unique identifier of the document.
     * 
     * @return The document's unique identifier.
     */
    public long getDocId() {
        return docId;
    }

    /**
     * Set the unique identifier of the document.
     * 
     * @param docId The document's unique identifier to set.
     */
    public void setDocId(long docId) {
        this.docId = docId;
    }

    /**
     * Get the name of the document.
     * 
     * @return The name of the document.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the document.
     * 
     * @param name The name of the document to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the binary data of the document.
     * 
     * @return The binary data of the document as a byte array.
     */
    public byte[] getData() {
        return data;
    }

    /**
     * Set the binary data of the document.
     * 
     * @param data The binary data of the document as a byte array to set.
     */
    public void setData(byte[] data) {
        this.data = data;
    }

    /**
     * Default constructor for the Document class.
     */
    public Document() {
    	// Default constructor
    }
}
