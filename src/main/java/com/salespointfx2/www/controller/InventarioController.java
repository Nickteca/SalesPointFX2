package com.salespointfx2.www.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.salespointfx2.www.dto.InventarioDetalleDTO;
import com.salespointfx2.www.model.SucursalProducto;
import com.salespointfx2.www.service.SucursalProductoService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

@Component
public class InventarioController implements Initializable {
	@Autowired
	private SucursalProductoService sps;

	@FXML
	private TableColumn<InventarioDetalleDTO, String> columnCategoria;

	@FXML
	private TableColumn<InventarioDetalleDTO, Integer> columnId;

	@FXML
	private TableColumn<InventarioDetalleDTO, Float> columnInventario;

	@FXML
	private TableColumn<InventarioDetalleDTO, String> columnNombre;

	@FXML
	private TableColumn<InventarioDetalleDTO, Float> columnPrecio;

	@FXML
	private TableView<InventarioDetalleDTO> tableInventario;
	private ObservableList<InventarioDetalleDTO> productosList;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		columnId.setCellValueFactory(new PropertyValueFactory<>("idSucursalProducto"));
		columnNombre.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
		columnCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
		columnPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
		columnInventario.setCellValueFactory(new PropertyValueFactory<>("inventario"));

		cargarProductos();
	}

	private void cargarProductos() {
		try {
			List<SucursalProducto> lsp = sps.getAllProductosOnly();
			if (!lsp.isEmpty()) {
				List<InventarioDetalleDTO> dtos = lsp.stream().map(sp -> new InventarioDetalleDTO(sp.getIdSucursalProducto(), sp.getProductoIdProducto().getNombreProducto(),
						sp.getCategoriaIdCategoria().getNombreCategoria(), sp.getInventario(), sp.getPrecio())).collect(Collectors.toList());
				productosList = FXCollections.observableArrayList(dtos);
				tableInventario.setItems(productosList);
			} else {
				Alert infoAlert = new Alert(AlertType.ERROR);
				infoAlert.setTitle("Alerta");
				infoAlert.setHeaderText("Aprece que no carga los productos");
				infoAlert.setContentText("Al parecer sta empty");
				infoAlert.showAndWait();
			}
		} catch (Exception e) {
			Alert infoAlert = new Alert(AlertType.ERROR);
			infoAlert.setTitle("InventarioController");
			infoAlert.setHeaderText("Tenemos problemas con esta clase");
			infoAlert.setContentText(this.toString());
			infoAlert.showAndWait();
		}
	}
}
