package com.salespointfx2.www.service;

import java.util.Arrays;
import java.util.List;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.anastaciocintra.escpos.EscPos;
import com.github.anastaciocintra.escpos.EscPosConst;
import com.github.anastaciocintra.escpos.Style;
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
			String barcode = "123456789012"; // El código de barras que quieres imprimir

			// Comando para imprimir código de barras EAN13
			byte[] barcodeCommand = new byte[] { 0x1D, 0x6B, 0x02, // Comando ESC/POS para código de barras
					(byte) barcode.length(), // Longitud del código
					0x00, 0x01, 0x03, // Parámetros adicionales si son necesarios
			};

			// Convertir la cadena del código de barras en un array de bytes y agregar al
			// comando
			for (char c : barcode.toCharArray()) {
				barcodeCommand = Arrays.copyOf(barcodeCommand, barcodeCommand.length + 1);
				barcodeCommand[barcodeCommand.length - 1] = (byte) c;
			}
			// Escribir el código de barras
			escpos.writel(barcodeCommand);
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
			infoAlert.setContentText(e.getMessage() + " " + e.getCause());
			infoAlert.showAndWait();
		}
	}

	public void codigoBarras() {

		// Cortar el papel (opcional)
		escpos.cut(EscPos.CutMode.FULL);
	}
}
