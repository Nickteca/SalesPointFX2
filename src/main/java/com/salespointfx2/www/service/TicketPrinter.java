package com.salespointfx2.www.service;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import org.springframework.stereotype.Service;

import com.github.anastaciocintra.escpos.EscPos;
import com.github.anastaciocintra.escpos.EscPosConst;
import com.github.anastaciocintra.escpos.Style;
import com.github.anastaciocintra.output.PrinterOutputStream;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

@Service
public class TicketPrinter {

	public void printTicket(String text) {
		try {
			// Obtener la impresora predeterminada
			PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();

			if (defaultPrintService == null) {
				System.out.println("No hay una impresora predeterminada configurada.");
				throw new Exception("No hay impresora pero se registro la venta");
			}

			// Nombre de la impresora predeterminada
			System.out.println("Usando impresora: " + defaultPrintService.getName());

			// Crear el stream de la impresora
			PrinterOutputStream printerOutputStream = new PrinterOutputStream(defaultPrintService);
			EscPos escpos = new EscPos(printerOutputStream);

			// Estilo de texto grande y centrado
			Style titleStyle = new Style().setFontSize(Style.FontSize._2, Style.FontSize._2).setJustification(EscPosConst.Justification.Center);

			escpos.writeLF(titleStyle, "TIENDA XYZ");
			escpos.writeLF("Fecha: 19/02/2025");
			escpos.writeLF("----------------------");
			escpos.writeLF("Producto   Cant Precio");
			escpos.writeLF("Coca-Cola   2   $25.00");
			escpos.writeLF("Sabritas    1   $15.00");
			escpos.writeLF("----------------------");
			escpos.writeLF(titleStyle, "Total: $40.00");
			escpos.writeLF("\n\n¡Gracias por su compra!");

			// Cortar papel y cerrar conexión
			escpos.feed(5);
			escpos.cut(EscPos.CutMode.FULL);
			// Suponiendo que escpos es una instancia de una clase que tiene el método write
			byte[] abrirCaja = new byte[] { 0x1B, 0x70, 0x00, 0x19, (byte) 0xFA };

			// Escribe los bytes al outputStream (suponiendo que escpos es un flujo de
			// salida)
			escpos.write(abrirCaja, 0, abrirCaja.length); // 0 es el offset y abrirCaja.length es la longitud

			escpos.close();

			System.out.println("Ticket impreso correctamente.");

		} catch (Exception e) {
			Alert infoAlert = new Alert(AlertType.INFORMATION);
			infoAlert.setTitle("Problema de impresora");
			infoAlert.setHeaderText("Alun detalle pasa con la impresosar");
			infoAlert.setContentText(e.getMessage());
			infoAlert.showAndWait();
		}
	}
}
