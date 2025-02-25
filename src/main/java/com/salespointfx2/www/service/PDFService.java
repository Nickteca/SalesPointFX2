package com.salespointfx2.www.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import jakarta.mail.MessagingException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

@Service
public class PDFService {
	@Autowired
	private EmailService emailService;

	public void createPdf(String fileName) throws MessagingException {
		try {
			// Crear un archivo PDF en la ruta especificada
			PdfWriter writer = new PdfWriter(fileName);

			// Crear un PdfDocument con el escritor
			PdfDocument pdf = new PdfDocument(writer);

			// Crear un documento iText
			Document document = new Document(pdf);

			// Agregar contenido al PDF
			document.add(new Paragraph("¡Hola, este es un PDF generado con iText 9.1.0!"));
			document.add(new Paragraph("Fecha de venta: " + "2025-02-24"));
			document.add(new Paragraph("Total: $100.00"));

			// Cerrar el documento
			document.close();
			emailService.sendEmail("isaaclunaavila@gmail.com", "Prueba", "Lo qu elleva el texto mas descriptivo", fileName);

			// System.out.println("PDF generado correctamente en: " + fileName);
		} catch (IOException e) {
			Alert infoAlert = new Alert(AlertType.ERROR);
			infoAlert.setTitle("error con el PDF");
			infoAlert.setHeaderText("Tiene problemas crar el pdf");
			infoAlert.setContentText(e.getMessage() + " " + e.getCause());
			infoAlert.showAndWait();
		}
	}
}
