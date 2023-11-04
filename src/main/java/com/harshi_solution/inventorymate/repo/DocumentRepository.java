package com.harshi_solution.inventorymate.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harshi_solution.inventorymate.entities.Document;

public interface DocumentRepository extends JpaRepository<Document, Long> {

}
