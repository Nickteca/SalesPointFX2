package com.salespointfx2.www.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.salespointfx2.www.dto.VentaCorteDTO;
import com.salespointfx2.www.model.Venta;
import com.salespointfx2.www.model.VentaDetalle;
import com.salespointfx2.www.service.VentaService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

@Component
public class VentaCorteController implements Initializable {
	@Autowired
	private VentaService vs;

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

	private ObservableList<VentaCorteDTO> ventaCorte;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		columnId.setCellValueFactory(new PropertyValueFactory<>("idSucursalProducto"));
		columnNombreproducto.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
		columnUnidades.setCellValueFactory(new PropertyValueFactory<>("unidades"));
		columnPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
		columnSubtotal.setCellValueFactory(new PropertyValueFactory<>("subtotal"));
		cargarVenta();
	}

	private void cargarVenta() {
		try {
			List<VentaCorteDTO> vcd = new ArrayList<VentaCorteDTO>();
			List<Venta> lv = vs.getVentaCorte();
			if (!lv.isEmpty()) {
				for (Venta venta : lv) {
					List<VentaDetalle> lvd = venta.getVentaDetalleList();
					for (VentaDetalle vd : lvd) {
						VentaCorteDTO vc = new VentaCorteDTO();
						vc.setIdSucursalProducto(vd.getSucursalProductoIdSucursalProducto().getIdSucursalProducto());
						vc.setNombreProducto(vd.getSucursalProductoIdSucursalProducto().getProductoIdProducto().getNombreProducto());
						vc.setPrecio(vd.getPrecio());
						vc.setSubtotal(vd.getSubTotal());
						vc.setUnidades(vd.getCantidad());
						vcd.add(vc);
					}
				}
				ventaCorte = FXCollections.observableArrayList(vcd);
				tViewVentaCorte.setItems(ventaCorte);

			} else {
				Alert infoAlert = new Alert(AlertType.WARNING);
				infoAlert.setTitle("Alerta");
				infoAlert.setHeaderText("No hay ventas segun este chow");
				infoAlert.setContentText("No tenemos ventas");
				infoAlert.showAndWait();
			}
		} catch (Exception e) {
			Alert infoAlert = new Alert(AlertType.ERROR);
			infoAlert.setTitle("Error");
			infoAlert.setHeaderText("error al traer la ventas por dias");
			infoAlert.setContentText(e.getMessage() + " " + e.getCause());
			infoAlert.showAndWait();
		}
	}

}
