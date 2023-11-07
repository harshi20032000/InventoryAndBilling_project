package com.harshi_solution.inventorymate.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.harshi_solution.inventorymate.entities.Order;
import com.harshi_solution.inventorymate.entities.OrderLineItem;
import com.harshi_solution.inventorymate.entities.OrderStatusHistory;
import com.harshi_solution.inventorymate.entities.Payment;
import com.harshi_solution.inventorymate.service.OrderService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Component
public class PdfGenerator {
	private static final Logger LOGGER = LoggerFactory.getLogger(PdfGenerator.class);

	@Autowired
	private OrderService orderService;

	public void generateFullOrderDetails(Long orderId, String filepath) {
		Order order = orderService.getOrderById(orderId);
		LOGGER.info("Inside generateFullOrderDetails() on OrderDetailsPdfGenerator");
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream(filepath));
			document.open();
			document.add(generateOrderDetailsTable(order));
			document.close();
		} catch (FileNotFoundException | DocumentException e) {
			LOGGER.error("Error - " + e);
			e.printStackTrace();
		}
	}

	public Element generateOrderDetailsTable(Order order) {
		PdfPTable pdfTable = new PdfPTable(2);
		PdfPCell cell = new PdfPCell(new Phrase(
				"-------------------------------------------Order Details-------------------------------------------"));
		cell.setColspan(2);
		pdfTable.addCell(cell);

		// Display Order Details
		pdfTable.addCell("Order ID:");
		pdfTable.addCell(order.getOrderId().toString());

		pdfTable.addCell("Order Date:");
		pdfTable.addCell(order.getOrderDate().toString());

		// Display one empty line for Reps
		PdfPCell emptySpaceForCell = new PdfPCell(new Phrase(
				"-------------------------------------------------------------------------------------------------------"));
		emptySpaceForCell.setColspan(2);
		pdfTable.addCell(emptySpaceForCell);

		// Display Selected Rep Details
		if (order.getReps() != null) {
			pdfTable.addCell("Reps ID:");
			pdfTable.addCell(order.getReps().getRepId().toString());

			pdfTable.addCell("Reps Name:");
			pdfTable.addCell(order.getReps().getRepName());

			pdfTable.addCell("Reps Location:");
			pdfTable.addCell(order.getReps().getRepLocation());
		}

		// Display one empty line for Reps
		pdfTable.addCell(emptySpaceForCell);

		// Display Party Details
		if (order.getParty() != null) {
			pdfTable.addCell("Party ID:");
			pdfTable.addCell(order.getParty().getPartyId().toString());

			pdfTable.addCell("Party Name:");
			pdfTable.addCell(order.getParty().getPartyName());

			pdfTable.addCell("Party Location:");
			pdfTable.addCell(order.getParty().getPartyLocation());
		}

		// Display one empty line for Orderlineitems to start
		pdfTable.addCell(emptySpaceForCell);

		// Display Order Line Items
		PdfPCell orderLineItemsCell = new PdfPCell(new Phrase("Order Line Items"));
		orderLineItemsCell.setColspan(2);
		pdfTable.addCell(orderLineItemsCell);

		PdfPTable lineItemsTable = new PdfPTable(4);
		lineItemsTable.setWidthPercentage(100); // Set the table width to 100%

		// Add table headers
		lineItemsTable.addCell("Product");
		lineItemsTable.addCell("Rate");
		lineItemsTable.addCell("Quantity");
		lineItemsTable.addCell("Total Price");

		for (OrderLineItem lineItem : order.getOrderLineItems()) {
			lineItemsTable.addCell(lineItem.getProduct() != null ? lineItem.getProduct().getBrandName() : "N/A");
			lineItemsTable.addCell(lineItem.getRate().toString());
			lineItemsTable.addCell(Integer.toString(lineItem.getQuantity()));
			lineItemsTable.addCell(lineItem.getRate().multiply(BigDecimal.valueOf(lineItem.getQuantity())).toString());
		}

		PdfPCell orderLineItemsListCell = new PdfPCell(lineItemsTable);
		orderLineItemsListCell.setColspan(2);
		pdfTable.addCell(orderLineItemsListCell);

		// Display Total Bill Amount, Remaining Amount, and Total Order Quantity
		pdfTable.addCell("Total Order Quantity:");
		pdfTable.addCell(order.getTotalOrderQuantity().toString());

		pdfTable.addCell("Total Bill Amount:");
		pdfTable.addCell(order.getTotalBillAmount().toString());

		pdfTable.addCell("Remaining Amount:");
		pdfTable.addCell(order.getRemainingBillAmount().toString());

		// Display one empty line for next item to start
		pdfTable.addCell(emptySpaceForCell);

		// Display Transport Details
		if (order.getTransportAndBuiltNumber() != null) {
			pdfTable.addCell("Transport Name:");
			pdfTable.addCell(order.getTransportAndBuiltNumber().getTransport().getTransportName());

			pdfTable.addCell("Contact Details:");
			pdfTable.addCell(order.getTransportAndBuiltNumber().getTransport().getContactDetails());

			pdfTable.addCell("Bilty No:");
			pdfTable.addCell(order.getTransportAndBuiltNumber().getBuiltNumber());
		}

		// Display one empty line for Payment Details to start
		pdfTable.addCell(emptySpaceForCell);

		// Display Payment Details
		PdfPCell paymentDetailsCell = new PdfPCell(new Phrase("Payment Details"));
		paymentDetailsCell.setColspan(2);
		pdfTable.addCell(paymentDetailsCell);

		PdfPTable paymentTable = new PdfPTable(4);
		paymentTable.setWidthPercentage(100); // Set the table width to 100%

		// Add table headers for payment details
		paymentTable.addCell("Date");
		paymentTable.addCell("Type");
		paymentTable.addCell("Mode");
		paymentTable.addCell("Amount");

		// Loop through payments and add them to the payment table
		for (Payment payment : order.getPayments()) {
			paymentTable.addCell(payment.getPayDate().toString());
			paymentTable.addCell(payment.getPayType());
			paymentTable.addCell(payment.getPayMode());
			paymentTable.addCell(payment.getPayAmount().toString());
		}

		PdfPCell paymentListCell = new PdfPCell(paymentTable);
		paymentListCell.setColspan(2);
		pdfTable.addCell(paymentListCell);

		/// Display one empty line for Order Status History to start
		pdfTable.addCell(emptySpaceForCell);

		// Display Order Status History
		PdfPCell statusHistoryCell = new PdfPCell(new Phrase("Order Status History"));
		statusHistoryCell.setColspan(2);
		pdfTable.addCell(statusHistoryCell);

		PdfPTable statusHistoryTable = new PdfPTable(2);
		statusHistoryTable.setWidthPercentage(100); // Set the table width to 100%

		// Add table headers for status history
		statusHistoryTable.addCell("Update Timestamp");
		statusHistoryTable.addCell("Status");

		// Loop through status history and add them to the status history table
		for (OrderStatusHistory change : order.getStatusHistory()) {
			statusHistoryTable.addCell(change.getUpdateTimestamp().toString());
			statusHistoryTable.addCell(change.getOrderStatus());
		}

		PdfPCell statusHistoryListCell = new PdfPCell(statusHistoryTable);
		statusHistoryListCell.setColspan(2);
		pdfTable.addCell(statusHistoryListCell);

		// Display one empty line
		PdfPCell emptyRow = new PdfPCell(new Phrase(" "));
		emptyRow.setColspan(2);
		pdfTable.addCell(emptyRow);

		// Display computer generated wala message
		PdfPCell computerGeneratedMessage = new PdfPCell(
				new Phrase("                                 This is computer generated pdf not valid unless signed."));
		computerGeneratedMessage.setColspan(2);
		pdfTable.addCell(computerGeneratedMessage);
		// Display one empty line
		pdfTable.addCell(emptyRow);

		return pdfTable;
	}

}
