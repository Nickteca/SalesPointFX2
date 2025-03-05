package com.salespointfx2.www.service;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.anastaciocintra.escpos.EscPos;
import com.github.anastaciocintra.escpos.EscPosConst;
import com.github.anastaciocintra.escpos.Style;
import com.github.anastaciocintra.output.PrinterOutputStream;
import com.salespointfx2.www.model.Recoleccion;

@Service
public class TicketRecoleccionService {
	@Autowired
	private RecoleccionService rs;

	public void imprimirRecoleccion(Recoleccion r) {
		try {
			PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();

			if (defaultPrintService == null) {
				System.out.println("No hay una impresora predeterminada configurada.");
				throw new Exception("No hay impresora pero se registro la venta");
			}

			// Crear el stream de la impresora
			PrinterOutputStream printerOutputStream = new PrinterOutputStream(defaultPrintService);
			EscPos escpos = new EscPos(printerOutputStream);

			// 🔹 ESTILO: Título (Nombre de la empresa)
			Style titleStyle = new Style().setFontSize(Style.FontSize._2, Style.FontSize._2)
					.setJustification(EscPosConst.Justification.Center);

			// 🔹 ESTILO: Subtítulo (Sucursal y dirección)
			Style subtitleStyle = new Style().setFontSize(Style.FontSize._1, Style.FontSize._1)
					.setJustification(EscPosConst.Justification.Center);

			// 🔹 ESTILO: Encabezado de productos
			Style headerStyle = new Style().setBold(true).setFontSize(Style.FontSize._1, Style.FontSize._1)
					.setJustification(EscPosConst.Justification.Left_Default);

			// 🔹 ESTILO: Texto normal para los productos
			Style productStyle = new Style().setFontSize(Style.FontSize._1, Style.FontSize._1)
					.setJustification(EscPosConst.Justification.Left_Default);

			// 🔹 ESTILO: Total (Negrita y tamaño grande)
			Style totalStyle = new Style().setBold(true).setFontSize(Style.FontSize._2, Style.FontSize._2)
					.setJustification(EscPosConst.Justification.Right);

			//
			Style fontA = new Style().setFontName(Style.FontName.Font_A_Default) // Usa Font A (normal)
					.setFontSize(Style.FontSize._1, Style.FontSize._1);

			Style fontB = new Style().setFontName(Style.FontName.Font_B).setBold(true); // Usa Font B (más pequeño)

			// 🏪 IMPRIMIR ENCABEZADO
			escpos.writeLF(titleStyle, r.getSucursalIdSucursal().getEmpresaIdEmpresa().getNombreEmpresa());
			escpos.writeLF(subtitleStyle, r.getSucursalIdSucursal().getNombreSucursal());
			escpos.writeLF(subtitleStyle,
					r.getSucursalIdSucursal().getCalleSucursal() + " " + r.getSucursalIdSucursal().getCiudadSucursal()
							+ " " + r.getSucursalIdSucursal().getEstadoSucursal());
			escpos.writeLF(fontB, "----------------------------------------------------------------");
			
		} catch (Exception e) {

		} finally {

		}
	}
}
