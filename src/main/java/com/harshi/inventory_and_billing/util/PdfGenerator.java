package com.harshi.inventory_and_billing.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PdfGenerator {
	private static final Logger LOGGER = LoggerFactory.getLogger(PdfGenerator.class);
//	
//	
//	public void generateItinerary(Reservation reservation, String filepath) {
//		LOGGER.info("Inside generateItinerary() on PdfGenerator");
//		Document document = new Document();
//		try {
//			PdfWriter.getInstance(document, new FileOutputStream(filepath));
//			document.open();
//			document.add(generateTable(reservation));
//			document.close();
//		} catch (FileNotFoundException | DocumentException e) {
//			LOGGER.error("errorMessage - "+e);
//			e.printStackTrace();
//		}
//		
//		
//	}
//
//	private Element generateTable(Reservation reservation) {
//		PdfPTable pdfTable = new PdfPTable(2);
//		
//		PdfPCell cell = new PdfPCell(new Phrase("Flight Itinerary"));
//		cell.setColspan(2);
//		pdfTable.addCell(cell);
//		
//		cell = new PdfPCell(new Phrase("Flight Details: "));
//		cell.setColspan(2);
//		pdfTable.addCell(cell);
//		
//		pdfTable.addCell("Reservation Id: ");
//		pdfTable.addCell(reservation.getId().toString());
//		
//		cell = new PdfPCell(new Phrase("Flight Id: "));
//		pdfTable.addCell(cell);
//		pdfTable.addCell(reservation.getFlight().getId().toString());
//		
//		cell = new PdfPCell(new Phrase("Departure City: "));
//		pdfTable.addCell(cell);
//		pdfTable.addCell(reservation.getFlight().getDepartureCity());
//		
//		cell = new PdfPCell(new Phrase("Departure Time: "));
//		pdfTable.addCell(cell);
//		pdfTable.addCell(reservation.getFlight().getEstimatedDepartureTime().toString());
//		
//		cell = new PdfPCell(new Phrase("Arrival City: "));
//		pdfTable.addCell(cell);
//		pdfTable.addCell(reservation.getFlight().getArrivalCity());
//		
//		cell = new PdfPCell(new Phrase("PassengerName: "));
//		pdfTable.addCell(cell);
//		pdfTable.addCell(reservation.getPassenger().getFirstName()+" "+ reservation.getPassenger().getLastName());
//		
//		cell = new PdfPCell(new Phrase("Passenger Email: "));
//		pdfTable.addCell(cell);
//		pdfTable.addCell(reservation.getPassenger().getEmail());
//		
//		cell = new PdfPCell(new Phrase("Passenger Phone: "));
//		pdfTable.addCell(cell);
//		pdfTable.addCell(reservation.getPassenger().getPhone());
//		
//		cell = new PdfPCell(new Phrase("Has the Passenger checkedIn: "));
//		pdfTable.addCell(cell);
//		pdfTable.addCell(reservation.getCheckedIn().toString());
//		
//		cell = new PdfPCell(new Phrase("Number of Bags: "));
//		pdfTable.addCell(cell);
//		pdfTable.addCell(Integer.toString(reservation.getNumOfBags()));
//		
//		
//		
//		return pdfTable;
//	}
//	
}
