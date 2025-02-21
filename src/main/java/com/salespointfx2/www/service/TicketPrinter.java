package com.salespointfx2.www.service;

import java.awt.*;
import java.awt.print.*;
import javax.print.*;

public class TicketPrinter {
	public void printTicket(String text) {
		try {
			// Obtener la impresora predeterminada
			PrintService printService = PrintServiceLookup.lookupDefaultPrintService();
			if (printService == null) {
				System.out.println("No hay una impresora predeterminada.");
				return;
			}

			// Crear un trabajo de impresión
			PrinterJob printerJob = PrinterJob.getPrinterJob();
			printerJob.setPrintService(printService);

			// Configurar contenido a imprimir
			printerJob.setPrintable(new Printable() {
				@Override
				public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException {
					if (pageIndex > 0) {
						return NO_SUCH_PAGE;
					}

					// Configurar fuente y tamaño
					Graphics2D g2d = (Graphics2D) g;
					g2d.setFont(new Font("Monospaced", Font.PLAIN, 10));

					int y = 50; // Posición inicial
					for (String line : text.split("\n")) {
						g2d.drawString(line, 50, y);
						y += 15; // Espaciado entre líneas
					}

					return PAGE_EXISTS;
				}
			});

			// Enviar a imprimir sin diálogo
			printerJob.print();
			System.out.println("Ticket enviado a la impresora.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
