package com.harshi_solution.inventorymate.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.harshi_solution.inventorymate.entities.Document;
import com.harshi_solution.inventorymate.entities.Payment;
import com.harshi_solution.inventorymate.service.DocumentService;
import com.harshi_solution.inventorymate.service.PaymentService;

@Controller
public class PaymentController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PaymentController.class);

	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private DocumentService documentService;
	
	
    /**
     * Display the details of a specific transport by transportId.
     *
     * @param transportId The ID of the transport to display.
     * @param model Model for adding attributes to the view.
     * @return The view name for the transport details.
     */
    @GetMapping("/paymentDetails/{payId}")
    public String showTPaymentDetails(@PathVariable Long payId, Model model) {
        LOGGER.info("Displaying payment details for payment with ID: {}", payId);
        Payment payment = paymentService.getPaymentById(payId);
        model.addAttribute("payment", payment);
        return "paymentView/paymentDetails";
    }
    
    @GetMapping("/viewDocument/{payId}")
    public String viewDocument(@PathVariable Long payId, Model model) {
        Payment payment = paymentService.getPaymentById(payId);

        if (payment.getDocument() != null) {
            model.addAttribute("document", payment.getDocument());
            model.addAttribute("orderId", payment.getOrder().getOrderId());

            return "paymentView/viewDocument"; // Replace "viewDocument" with your actual Thymeleaf view name
        } else {
            // Handle the case where no document is found
            return "documentNotFound"; // Create a view for displaying a message about document not found
        }
    }
    
    @GetMapping("/document/{docId}")
    public ResponseEntity<byte[]> viewDocument(@PathVariable Long docId) {
        Document document = documentService.getDocumentById(docId);

        if (document != null && document.getData() != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF); // Adjust the content type based on your document type

            // You might want to provide a meaningful file name here
            headers.setContentDispositionFormData("attachment", document.getName()+ ".pdf");

            // Return the document data as a ResponseEntity
            return new ResponseEntity<>(document.getData(), headers, HttpStatus.OK);
        }

        // Handle the case where no document is found
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
