package com.salespointfx2.www.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.salespointfx2.www.config.VentaConverter;
import com.salespointfx2.www.dto.VentaDetalleTabla;
import com.salespointfx2.www.model.Folio;
import com.salespointfx2.www.model.PaqueteProducto;
import com.salespointfx2.www.model.Producto;
import com.salespointfx2.www.model.Sucursal;
import com.salespointfx2.www.model.SucursalProducto;
import com.salespointfx2.www.model.Venta;
import com.salespointfx2.www.model.VentaDetalle;
import com.salespointfx2.www.repository.VentaDetalleRepo;
import com.salespointfx2.www.repository.VentaRepo;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

@Service
public class VentaService {
	@Autowired private VentaRepo vr;
	@Autowired private VentaDetalleService vds;
	@Autowired private FolioService fs;
	@Autowired private SucursalProductoService sps;
	@Autowired private PaqueteProductoService pps;

	@Autowired
	private SucursalService ss;
	@Autowired
	private VentaConverter ventaConverter;

	@Transactional
	public Venta saveVenta(String folio, float totalVenta, List<VentaDetalleTabla> dtos) {
		try {
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
			ventaGuardada.setVentaDetalleList(detalles);
			vds.saveAll(detalles);
			// ACTUALIZAMOS EL FOLIO
			fs.updateFolioVenta(fs.getFolioVenta());
			//
			// NO CRAGA EL NOMRE DEL PRODUCTO SEL SUCURSALPRODUCTO
			for (VentaDetalle ventaDetalle : detalles) {
				SucursalProducto sp = sps.getByIdWithProducto(
						ventaDetalle.getSucursalProductoIdSucursalProducto().getIdSucursalProducto());
				if (sp != null) {
					Producto p = sp.getProductoIdProducto();
					if (p.isEsPaquete()) {
						List<PaqueteProducto> productosDelPaquete = pps.getPaqueteProductoById(p);
						for (PaqueteProducto pp : productosDelPaquete) {	
							float nuevoInventario = sp.getInventario()-(pp.getCantidad()*ventaDetalle.getCantidad());
							sp.setInventario(nuevoInventario);
							sps.updateInventory(sp);
							/*SucursalProducto sp = sucursalProductoRepository
									.findBySucursalIdSucursalAndProductoIdProducto(
											ventaGuardada.getSucursalIdSucursal(),
											pp.getProductoIdProducto().getIdProducto());

							if (sp != null) {
								// Descontamos la cantidad correspondiente del producto dentro del paquete
								float nuevoInventario = sp.getInventario()
										- (pp.getCantidad() * vd.getCantidadVendida());
								sp.setInventario(nuevoInventario);
								sucursalProductoRepository.save(sp);
							} else {
								throw new RuntimeException("Producto no encontrado en inventario para la sucursal");
							}*/
						}
					}

				} else {
					throw new Exception("❌ Alerta: SucursalProducto no existe.");
				}

			}
			return ventaGuardada;
		} catch (Exception e) {
			Alert infoAlert = new Alert(AlertType.ERROR);
			infoAlert.setTitle("Error al ingresar la venta");
			infoAlert.setHeaderText("Error en la transaccion");
			infoAlert.setContentText(e.getMessage() + " " + e.getCause() + " " + e.getStackTrace());
			infoAlert.showAndWait();
			return null;
		}

	}
}