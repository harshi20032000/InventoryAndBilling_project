package com.harshi.inventory_and_billing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harshi.inventory_and_billing.entities.Document;
import com.harshi.inventory_and_billing.repo.DocumentRepository;

/**
 * The DocumentServiceImpl class provides implementations for managing documents
 * in the system.
 */
@Service
public class DocumentServiceImpl implements DocumentService {

	@Autowired
	private DocumentRepository documentRepository;

	/**
	 * Saves a document in the system.
	 *
	 * @param document The document to be saved.
	 * @return The saved document.
	 */
	@Override
	public Document saveDocument(Document document) {
		return documentRepository.save(document);
	}

	/**
	 * Retrieves a document by its unique identifier.
	 *
	 * @param id The unique identifier of the document.
	 * @return The document with the specified identifier, or null if not found.
	 */
	@Override
	public Document getDocumentById(Long id) {
		return documentRepository.findById(id).orElse(null);
	}
}
