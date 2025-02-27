package com.salespointfx2.www.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.salespointfx2.www.model.Gasto;
import com.salespointfx2.www.service.GastoService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

@Component
public class GastoController implements Initializable {
	@Autowired
	private GastoService gs;
	@FXML
	private ChoiceBox<Gasto> cBoxServicio;

	@FXML
	private DatePicker dateFecha;

	@FXML
	private TextField textCantidad;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		/*
		 * AQUI LE PONEMOS UN ESCUCHAR DL CHOICEBOX PARA QUE EN UANTO CAMBIE DE
		 * SELECCION HACE COSITAS
		 */
		/*
		 * cBoxServicio.getSelectionModel().selectedItemProperty().addListener(new
		 * ChangeListener<Gasto>() {
		 * 
		 * @Override public void changed(ObservableValue<? extends Gasto> observable,
		 * Gasto oldValue, Gasto newValue) { if (newValue != null) { // Obtener la lista
		 * de GastoSucursal asociada al Gasto seleccionado List<GastoSucursal>
		 * gastoSucursalList = newValue.getGastoSucursalList();
		 * 
		 * // Solo mostrar el primer GastoSucursal (asumiendo que sólo quieres el
		 * primero) if (!gastoSucursalList.isEmpty()) { GastoSucursal gastoSucursal =
		 * gastoSucursalList.get(0); // Obtener el primero float monto =
		 * gastoSucursal.getMontoGasto(); // Monto LocalDate fecha =
		 * gastoSucursal.getFechaGasto(); // Fecha
		 * 
		 * // Mostrar los detalles en las etiquetas
		 * textCantidad.setText("Monto a pagar: " + monto); dateFecha.setValue(fecha); }
		 * } } });
		 */
		dateFecha.setValue(LocalDate.now());
		cargarGasto();
	}

	private void cargarGasto() {
		try {
			List<Gasto> lg = gs.getGastos();
			// Convertir la lista de gastos a un ObservableList
			ObservableList<Gasto> observableGastos = FXCollections.observableArrayList(lg);

			// Establecer la lista de gastos al ChoiceBox
			cBoxServicio.setItems(observableGastos);
			// Opcional: seleccionar un valor por defecto si lo necesitas
			if (!lg.isEmpty()) {
				cBoxServicio.setValue(lg.get(0)); // Selecciona el primer gasto
			}
		} catch (Exception e) {
			Alert infoAlert = new Alert(AlertType.ERROR);
			infoAlert.setTitle("GastoController Error");
			infoAlert.setHeaderText("Error al cargar los gastos");
			infoAlert.setContentText(e.getMessage() + " " + e.getCause());
			infoAlert.showAndWait();
		}
	}
}
