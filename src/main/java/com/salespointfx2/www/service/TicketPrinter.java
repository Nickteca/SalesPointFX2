package com.salespointfx2.www.service;

import java.io.IOException;
import java.util.List;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.anastaciocintra.escpos.EscPos;
import com.github.anastaciocintra.escpos.EscPosConst;
import com.github.anastaciocintra.escpos.Style;
import com.github.anastaciocintra.escpos.barcode.QRCode;
import com.github.anastaciocintra.output.PrinterOutputStream;
import com.salespointfx2.www.model.SucursalProducto;
import com.salespointfx2.www.model.Venta;
import com.salespointfx2.www.model.VentaDetalle;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

@Service
public class TicketPrinter {
	@Autowired
	private EmpresaService es;
	@Autowired
	SucursalProductoService sps;

	public void printTicket(Venta venta) {
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

			// 🔹 ESTILO: Título (Nombre de la empresa)
			Style titleStyle = new Style().setFontSize(Style.FontSize._2, Style.FontSize._2).setJustification(EscPosConst.Justification.Center);

			// 🔹 ESTILO: Subtítulo (Sucursal y dirección)
			Style subtitleStyle = new Style().setFontSize(Style.FontSize._1, Style.FontSize._1).setJustification(EscPosConst.Justification.Center);

			// 🔹 ESTILO: Encabezado de productos
			Style headerStyle = new Style().setBold(true).setFontSize(Style.FontSize._1, Style.FontSize._1).setJustification(EscPosConst.Justification.Left_Default);

			// 🔹 ESTILO: Texto normal para los productos
			Style productStyle = new Style().setFontSize(Style.FontSize._1, Style.FontSize._1).setJustification(EscPosConst.Justification.Left_Default);

			// 🔹 ESTILO: Total (Negrita y tamaño grande)
			Style totalStyle = new Style().setBold(true).setFontSize(Style.FontSize._2, Style.FontSize._2).setJustification(EscPosConst.Justification.Right);

			//
			Style fontA = new Style().setFontName(Style.FontName.Font_A_Default) // Usa Font A (normal)
					.setFontSize(Style.FontSize._1, Style.FontSize._1);

			Style fontB = new Style().setFontName(Style.FontName.Font_B).setBold(true); // Usa Font B (más pequeño)
			;

			// 🏪 IMPRIMIR ENCABEZADO
			escpos.writeLF(titleStyle, es.getEmpresa().getNombreEmpresa());
			escpos.writeLF(subtitleStyle, es.getEmpresa().getSucursalCollection().get(0).getNombreSucursal());
			escpos.writeLF(subtitleStyle, es.getEmpresa().getSucursalCollection().get(0).getCalleSucursal() + " " + es.getEmpresa().getSucursalCollection().get(0).getCiudadSucursal() + " "
					+ es.getEmpresa().getSucursalCollection().get(0).getEstadoSucursal());
			escpos.writeLF(subtitleStyle, "Folio: " + venta.getFolio());
			escpos.writeLF(subtitleStyle, "Fecha: " + venta.getCreatedAt());
			escpos.feed(1); // Línea en blanco

			// 📦 IMPRIMIR PRODUCTOS
			escpos.writeLF(headerStyle, String.format("%-24s %5s %8s %8s", "Producto", "Cant", "Precio", "Subtotal"));
			escpos.writeLF(fontB, "----------------------------------------------------------------");
			// Simulación de productos
			float total = 0;
			List<VentaDetalle> lvd = venta.getVentaDetalleList();
			for (VentaDetalle vd : lvd) {
				SucursalProducto sp = sps.getSucursalProductoById(vd.getSucursalProductoIdSucursalProducto().getIdSucursalProducto());
				// Productos (Ejemplo con 3 productos)
				total += vd.getSubTotal();
				String line1 = String.format("%-34s %5d %8s %10s", sp.getProductoIdProducto().getNombreProducto(), vd.getCantidad(), "$" + String.format("%,.0f", vd.getPrecio()), // Formateo de precio
																																													// sin decimales
						"$" + String.format("%,.0f", vd.getSubTotal())); // Formateo de subtotal sin decimales
				escpos.writeLF(fontB, line1);
			}
			escpos.writeLF(fontB, "----------------------------------------------------------------");
			escpos.writeLF(headerStyle.setBold(true), String.format("%-22s %5s %8s %8s", "", "", "Total", "$" + String.format("%,.0f", total)));
			// Código de barras EAN13

			// Cortar papel y cerrar conexión
			escpos.feed(5);
			escpos.cut(EscPos.CutMode.FULL);
			// Suponiendo que escpos es una instancia de una clase que tiene el método write
			byte[] abrirCaja = new byte[] { 0x1B, 0x70, 0x00, 0x19, (byte) 0xFA };

			// Escribe los bytes al outputStream (suponiendo que escpos es un flujo de
			// salida)
			escpos.write(abrirCaja, 0, abrirCaja.length); // 0 es el offset y abrirCaja.length es la longitud

			escpos.close();

		} catch (Exception e) {
			Alert infoAlert = new Alert(AlertType.ERROR);
			infoAlert.setTitle("Problema de impresora");
			infoAlert.setHeaderText("Alun detalle pasa con la impresosar, pro se registro la venta");
			infoAlert.setContentText(e.getMessage() + " " + e.getCause());
			infoAlert.showAndWait();
		}
	}

	public void abririCajon() {
		try {
			PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();

			if (defaultPrintService == null) {
				System.out.println("No hay una impresora predeterminada configurada.");
				throw new Exception("No hay impresora pero se registro la venta");
			}
			// Crear el stream de la impresora
			PrinterOutputStream printerOutputStream = new PrinterOutputStream(defaultPrintService);
			EscPos escpos = new EscPos(printerOutputStream);
			byte[] abrirCaja = new byte[] { 0x1B, 0x70, 0x00, 0x19, (byte) 0xFA };

			// Escribe los bytes al outputStream (suponiendo que escpos es un flujo de
			// salida)
			escpos.write(abrirCaja, 0, abrirCaja.length); // 0 es el offset y abrirCaja.length es la longitud

			escpos.close();
		} catch (Exception e) {
			Alert error = new Alert(AlertType.ERROR);
			error.setTitle("Error Abrir Cajon");
			error.setHeaderText("Al parecer no hay impresora por defaul o no esta encendida");
			error.setContentText(e.getMessage() + " \n" + e.getCause());
			error.show();
		}
	}

	public void codigoBarras() {
		try {
			// Buscar la impresora predeterminada
			PrintService printService = PrintServiceLookup.lookupDefaultPrintService();
			if (printService == null) {
				System.out.println("No se encontró una impresora.");
				return;
			}

			// Crear el stream de la impresora
			PrinterOutputStream printerOutputStream = new PrinterOutputStream(printService);
			EscPos escpos = new EscPos(printerOutputStream);

			// Crear y configurar el código QR
			QRCode qrCode = new QRCode();
			qrCode.setSize(10); // Tamaño del QR (1-16)
			qrCode.setModel(QRCode.QRModel._1_Default); // Modelo QR
			qrCode.setErrorCorrectionLevel(QRCode.QRErrorCorrectionLevel.QR_ECLEVEL_M_Default); // Nivel de corrección
			qrCode.setJustification(EscPos.Justification.Center); // Centrar QR

			// Enviar el QR a la impresora
			escpos.write(qrCode, "https://www.tiendaejemplo.com");

			escpos.feed(5); // Espaciado
			escpos.cut(EscPos.CutMode.FULL); // Corte de papel
			escpos.close();

			System.out.println("Código QR impreso correctamente.");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
