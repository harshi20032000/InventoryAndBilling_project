package com.harshi.inventory_and_billing.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harshi.inventory_and_billing.entities.Document;

public interface DocumentRepository extends JpaRepository<Document, Long> {

}
