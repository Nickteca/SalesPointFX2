package com.salespointfx2.www.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import org.ietf.jgss.GSSContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.salespointfx2.www.dto.GastoSucursalDTO;
import com.salespointfx2.www.dto.VentaCorteDTO;
import com.salespointfx2.www.model.Gasto;
import com.salespointfx2.www.model.GastoSucursal;
import com.salespointfx2.www.service.GastoService;
import com.salespointfx2.www.service.GastoSucursalService;
import com.salespointfx2.www.service.SucursalService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

@Component
public class GastoController implements Initializable {
	@Autowired
	private GastoService gs;
	@Autowired
	private GastoSucursalService gss;
	@Autowired
	private SucursalService ss;

	@FXML
	private Button btnRegistrar;
	@FXML
	private ChoiceBox<Gasto> cBoxServicio;

	@FXML
	private DatePicker dateFecha;

	@FXML
	private TextField textCantidad;

	@FXML
	private TableColumn<GastoSucursalDTO, LocalDate> columnFecha;

	@FXML
	private TableColumn<GastoSucursalDTO, String> columnGasto;

	@FXML
	private TableColumn<GastoSucursalDTO, Integer> columnId;

	@FXML
	private TableColumn<GastoSucursalDTO, Float> columnMonto;
	@FXML
	private TableView<GastoSucursalDTO> tViewGastos;
	private ObservableList<GastoSucursalDTO> gastosList;

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
		// Filtro para solo aceptar números y evitar que empiece con 0
		columnId.setCellValueFactory(new PropertyValueFactory<>("idGastoSucursal"));
		columnGasto.setCellValueFactory(new PropertyValueFactory<>("descripcionGasto"));
		columnFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
		columnMonto.setCellValueFactory(new PropertyValueFactory<>("monto"));
		columnMonto.setCellFactory(column -> new TableCell<GastoSucursalDTO, Float>() {
			@Override
			protected void updateItem(Float monto, boolean empty) {
				super.updateItem(monto, empty);
				if (empty || monto == null) {
					setText(null);
				} else {
					setText(String.format("$%.0f", monto));
				}
			}
		});

		UnaryOperator<TextFormatter.Change> filter = change -> {
			String newText = change.getControlNewText();
			if (newText.matches("[1-9][0-9]*") || newText.isEmpty()) {
				return change;
			}
			return null; // Rechaza la entrada si no cumple la condición
		};

		TextFormatter<String> textFormatter = new TextFormatter<>(filter);
		textCantidad.setTextFormatter(textFormatter);
		dateFecha.setValue(LocalDate.now());
		cargarGasto();
		cargarGastosTabla();
	}

	private void cargarGastosTabla() {
		List<GastoSucursal> lgs = new ArrayList<GastoSucursal>();
		List<GastoSucursalDTO> lgsdto = new ArrayList<GastoSucursalDTO>();
		lgs = gss.getAllGastosSucursalBySucursal();
		if (!lgs.isEmpty()) {
			for (GastoSucursal gasts : lgs) {
				GastoSucursalDTO gsdto = new GastoSucursalDTO();
				gsdto.setIdGastoSucursal(gasts.getIdGastoSucursal());
				gsdto.setDescripcionGasto(gasts.getGastoIdGastos().getDescripcionGasto());
				gsdto.setMonto(gasts.getMontoGasto());
				gsdto.setFecha(gasts.getFechaGasto());
				lgsdto.add(gsdto);
			}
			gastosList = FXCollections.observableArrayList(lgsdto);
			tViewGastos.setItems(gastosList);
		} else {
			Alert a = new Alert(AlertType.WARNING);
			a.setTitle("GastoController Error");
			a.setHeaderText("No hay Gastos de la sucursal");
			a.setContentText("Non encontre gastos para la sucursal activas");
			a.show();
		}
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

	@FXML
	void btnRegistrarGasto(ActionEvent event) {
		try {
			GastoSucursal gastoSucursal = new GastoSucursal(dateFecha.getValue(),
					Float.parseFloat(textCantidad.getText()), cBoxServicio.getSelectionModel().getSelectedItem(),
					ss.getSucursalActive().get());
			if (!gss.saveGastoSucursal(gastoSucursal).equals(null)) {
				textCantidad.setText("");
				cargarGastosTabla();
			} else {
				throw new Exception("Extar extrallendo null el insertar gastosucursal");
			}
		} catch (Exception e) {
			Alert infoAlert = new Alert(AlertType.ERROR);
			infoAlert.setTitle("GastoController Error");
			infoAlert.setHeaderText("Error al insertar el GastoSucursal");
			infoAlert.setContentText(e.getMessage() + " " + e.getCause());
			infoAlert.showAndWait();
		}

	}
}
