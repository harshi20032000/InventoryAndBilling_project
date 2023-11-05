package com.harshi_solution.inventorymate.service;

import com.harshi_solution.inventorymate.entities.Document;

public interface DocumentService {

	public Document saveDocument(Document document);

	public Document getDocumentById(Long id);

}
