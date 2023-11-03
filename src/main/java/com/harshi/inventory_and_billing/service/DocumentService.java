package com.harshi.inventory_and_billing.service;

import com.harshi.inventory_and_billing.entities.Document;

public interface DocumentService {

	public Document saveDocument(Document document);

	public Document getDocumentById(Long id);

}
