package com.salespointfx2.www.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.salespointfx2.www.dto.VentaCorteDTO;
import com.salespointfx2.www.model.Venta;
import com.salespointfx2.www.model.VentaDetalle;
import com.salespointfx2.www.service.VentaDetalleService;
import com.salespointfx2.www.service.VentaService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

@Component
public class VentaCorteController implements Initializable {
	@Autowired
	private VentaService vs;
	@Autowired
	VentaDetalleService vds;

	@FXML
	private TableColumn<VentaCorteDTO, Integer> columnId;

	@FXML
	private TableColumn<VentaCorteDTO, String> columnNombreproducto;

	@FXML
	private TableColumn<VentaCorteDTO, Float> columnPrecio;

	@FXML
	private TableColumn<VentaCorteDTO, Float> columnSubtotal;

	@FXML
	private TableColumn<VentaCorteDTO, Integer> columnUnidades;

	@FXML
	private Label lblTotal;

	@FXML
	private TableView<VentaCorteDTO> tViewVentaCorte;
	private ObservableList<VentaCorteDTO> productosList;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		columnId.setCellValueFactory(new PropertyValueFactory<>("idSucursalProducto"));
		columnNombreproducto.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
		columnPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
		columnPrecio.setCellFactory(column -> new TableCell<VentaCorteDTO, Float>() {
			@Override
			protected void updateItem(Float precio, boolean empty) {
				super.updateItem(precio, empty);
				if (empty || precio == null) {
					setText(null);
				} else {
					setText(String.format("$%.0f", precio));
				}
			}
		});
		columnSubtotal.setCellValueFactory(new PropertyValueFactory<>("subtotal"));
		columnSubtotal.setCellFactory(column -> new TableCell<VentaCorteDTO, Float>() {
			@Override
			protected void updateItem(Float subtotal, boolean empty) {
				super.updateItem(subtotal, empty);
				if (empty || subtotal == null) {
					setText(null);
				} else {
					setText(String.format("$%.0f", subtotal));
				}
			}
		});
		columnUnidades.setCellValueFactory(new PropertyValueFactory<>("unidades"));
		cargarVentas();
	}

	private void cargarVentas() {
		try {
			List<Venta> lv = new ArrayList<Venta>();
			List<VentaDetalle> lvd = new ArrayList<VentaDetalle>();
			List<VentaCorteDTO> lddto = new ArrayList<VentaCorteDTO>();
			Map<Integer, VentaCorteDTO> ventaMap = new HashMap<>();
			float total = 0;
			lv = vs.getVentaCorte();
			if (!lv.isEmpty()) {
				for (Venta venta : lv) {
					lvd = vds.getVentaDetalleXCorte(venta);
					if (!lvd.isEmpty()) {
						for (VentaDetalle vd : lvd) {
							int idSucursalProducto = vd.getSucursalProductoIdSucursalProducto().getIdSucursalProducto();
							float precio = vd.getPrecio();
							int cantidad = vd.getCantidad();
							float subtotal = vd.getSubTotal();
							String nombreProducto = vd.getSucursalProductoIdSucursalProducto().getProductoIdProducto()
									.getNombreProducto();

							if (ventaMap.containsKey(idSucursalProducto)) {
								// Si el producto ya existe, sumamos las unidades y el subtotal
								VentaCorteDTO vcExistente = ventaMap.get(idSucursalProducto);
								vcExistente.setUnidades(vcExistente.getUnidades() + cantidad);
								vcExistente.setSubtotal(vcExistente.getSubtotal() + subtotal);
							} else {
								// Si no existe, lo agregamos
								VentaCorteDTO vc = new VentaCorteDTO();
								vc.setIdSucursalProducto(idSucursalProducto);
								vc.setNombreProducto(nombreProducto);
								vc.setPrecio(precio);
								vc.setUnidades(cantidad);
								vc.setSubtotal(subtotal);
								ventaMap.put(idSucursalProducto, vc);
							}
							total += subtotal;
						}

					} else {
						Alert infoAlert = new Alert(AlertType.WARNING);
						infoAlert.setTitle("VentaController Error");
						infoAlert.setHeaderText("No cargo los detalles");
						infoAlert.setContentText("Un error por no cargar las ventas detalle");
						infoAlert.showAndWait();
					}
				}
				productosList = FXCollections.observableArrayList(ventaMap.values());
				tViewVentaCorte.setItems(productosList);
				lblTotal.setText(total + "");
			} else {
				Alert infoAlert = new Alert(AlertType.WARNING);
				infoAlert.setTitle("VentaController Vacio");
				infoAlert.setHeaderText("No hay ventas");
				infoAlert.setContentText("Aun no se han registrado ventas");
				infoAlert.showAndWait();
			}
		} catch (Exception e) {
			Alert infoAlert = new Alert(AlertType.ERROR);
			infoAlert.setTitle("VentaController Error");
			infoAlert.setHeaderText("Erroe en cargar Lista venta");
			infoAlert.setContentText(e.getMessage() + " " + e.getCause());
			infoAlert.showAndWait();
		}
	}
}
