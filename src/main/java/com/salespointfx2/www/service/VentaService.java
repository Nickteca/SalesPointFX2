package com.salespointfx2.www.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.salespointfx2.www.config.VentaConverter;
import com.salespointfx2.www.dto.VentaDetalleTabla;
import com.salespointfx2.www.model.PaqueteProducto;
import com.salespointfx2.www.model.Producto;
import com.salespointfx2.www.model.Sucursal;
import com.salespointfx2.www.model.SucursalProducto;
import com.salespointfx2.www.model.Venta;
import com.salespointfx2.www.model.VentaDetalle;
import com.salespointfx2.www.repository.VentaRepo;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

@Service
public class VentaService {
	@Autowired
	private TicketPrinter tps;
	@Autowired
	private PDFService pdfs;
	@Autowired
	private MovimientoCajaService mcs;
	@Autowired
	private VentaRepo vr;
	@Autowired
	private VentaDetalleService vds;
	@Autowired
	private FolioService fs;
	@Autowired
	private SucursalProductoService sps;
	@Autowired
	private PaqueteProductoService pps;

	@Autowired
	private SucursalService ss;
	@Autowired
	private VentaConverter ventaConverter;
	@Autowired
	private WhatsAppService was;

	@Transactional
	public Venta saveVenta(String folio, float totalVenta, List<VentaDetalleTabla> dtos) {
		String respuesta;
		try {
			if (mcs.getLastMovimientoCaja(ss.getSucursalActive()).get().getCreatedAt().isBefore(LocalDateTime.now())) {

				// Crear la entidad Venta
				Venta venta = new Venta();
				venta.setFolio(folio);
				venta.setTotalVenta(totalVenta);
				venta.setStatus(true);
				venta.setNaturalezaVenta('S'); // O según tu lógica
				venta.setCreatedAt(LocalDateTime.now());
				venta.setSucursalIdSucursal(new Sucursal(ss.getSucursalActive().get().getIdSucursal()));

				// Primero, guarda la venta para generar el ID
				Venta ventaGuardada = vr.save(venta);

				// Convertir los DTO a VentaDetalle y asignar la venta registrada
				List<VentaDetalle> detalles = ventaConverter.convertToVentaDetalle(dtos, ventaGuardada);
				ventaGuardada.setVentaDetalleList(vds.saveAll(detalles));

				// ACTUALIZAMOS EL FOLIO
				fs.updateFolioVenta(fs.getFolioVenta());
				//
				// NO CRAGA EL NOMRE DEL PRODUCTO SEL SUCURSALPRODUCTO
				for (VentaDetalle ventaDetalle : detalles) {
					SucursalProducto sp = sps.getSucursalProductoById(ventaDetalle.getSucursalProductoIdSucursalProducto().getIdSucursalProducto());
					Producto p = sp.getProductoIdProducto();
					if (p.isEsPaquete()) {
						List<PaqueteProducto> lpp = pps.getPaqueteProductoById(p);
						for (PaqueteProducto paquteProduto : lpp) {
							SucursalProducto spCompnente = sps.getSucursalProductoById(paquteProduto.getProductoIdProducto().getIdProducto());

							spCompnente.setInventario(spCompnente.getInventario() - (paquteProduto.getCantidad() * ventaDetalle.getCantidad()));
							sps.updateInventory(spCompnente);

							Alert infoAlert = new Alert(AlertType.INFORMATION);
							infoAlert.setTitle(paquteProduto.getPaqueteIdPaquete().getNombreProducto());
							infoAlert.setHeaderText("Contiene");
							infoAlert.setContentText(paquteProduto.getProductoIdProducto().getNombreProducto() + " Cantidad: " + paquteProduto.getCantidad());
							infoAlert.showAndWait();
						}
						// Restar la cantidad correspondiente al paquete

					} else {
						// 🔹 Si es un producto normal, restamos su stock directamente
						sp.setInventario(sp.getInventario() - ventaDetalle.getCantidad());
						sps.updateInventory(sp);
						Alert infoAlert = new Alert(AlertType.INFORMATION);
						infoAlert.setTitle("Producto");
						infoAlert.setHeaderText("Que producto se relaciona");
						infoAlert.setContentText(p.getNombreProducto());
						infoAlert.showAndWait();
					}
				}
				tps.printTicket(ventaGuardada);
				respuesta = was.sendWhatsAppMessage("4341327947", folio);
				pdfs.createPdf("C:/Users/Sistemas/Documents/" + folio + ".pdf");

				Alert infoAlert = new Alert(AlertType.INFORMATION);
				infoAlert.setTitle("Whatsapp");
				infoAlert.setHeaderText("Que es lo que no dice watsappp");
				infoAlert.setContentText(respuesta);
				infoAlert.showAndWait();
				return ventaGuardada;
			} else {
				throw new Exception("la fecha no es correcta");
			}
		} catch (

		Exception e) {
			Alert infoAlert = new Alert(AlertType.ERROR);
			infoAlert.setTitle("Error al ingresar la venta");
			infoAlert.setHeaderText("Error en la transaccion");
			infoAlert.setContentText(e.getMessage() + " " + e.getCause() + " " + e.getStackTrace());
			infoAlert.showAndWait();
			return null;
		}

	}
}