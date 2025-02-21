package com.salespointfx2.www.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.salespointfx2.www.config.VentaViewModel;
import com.salespointfx2.www.dto.VentaDetalleTabla;
import com.salespointfx2.www.model.Folio;
import com.salespointfx2.www.service.VentaService;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

@Component
public class CambioController implements Initializable {

	@Autowired
	private VentaViewModel vvm;
	@Autowired
	private VentaService vs;

	@FXML
	private Button btn0;

	@FXML
	private Button btn1;

	@FXML
	private Button btn100;

	@FXML
	private Button btn1000;

	@FXML
	private Button btn2;

	@FXML
	private Button btn20;

	@FXML
	private Button btn200;

	@FXML
	private Button btn3;

	@FXML
	private Button btn4;

	@FXML
	private Button btn5;

	@FXML
	private Button btn50;

	@FXML
	private Button btn500;

	@FXML
	private Button btn6;

	@FXML
	private Button btn7;

	@FXML
	private Button btn8;

	@FXML
	private Button btn9;

	@FXML
	private Button btnCancelar;

	@FXML
	private Button btnCobrar;

	@FXML
	private Button btnLimpiar;

	@FXML
	private Button btncantidadExacta;

	@FXML
	private TextField textCambio;

	@FXML
	private TextField textEfectivo;

	@FXML
	private TextField textTotal;
	// Bandera para controlar la primera edición de textEfectivo.
	private boolean primeraEdicion = true;

	private double totalVenta;
	private String f;
	private ObservableList<VentaDetalleTabla> productosVenta;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// Agregar listener a la escena para escuchar teclas sin depender del foco.
		// Se usa btnCobrar (o cualquier otro nodo que ya esté en la escena) para
		// obtener la escena.
		btnCobrar.sceneProperty().addListener((obs, oldScene, newScene) -> {
			if (newScene != null) {
				newScene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
					// Si se presiona F12, dispara el botón de cobrar.
					if (event.getCode() == KeyCode.F12) {
						btnCobrar.fire();
						event.consume();
					}
					// Si se presiona ESPACIO o ENTER, dispara el botón de cantidad exacta.
					else if (event.getCode() == KeyCode.SPACE || event.getCode() == KeyCode.ENTER) {
						btncantidadExacta.fire();
						event.consume();
					}
				});
			}
		});
	}

	@FXML
	void agregarEfectivo(ActionEvent event) {
		String digito = ((Button) event.getSource()).getText();
		handleNumero(textEfectivo, digito);
	}

	private void handleNumero(TextField textField, String digito) {
		// Si es la primera edición, se limpia el campo.
		if (primeraEdicion) {
			textField.clear();
			primeraEdicion = false;
		}
		// Agregar el dígito y actualizar caret
		textField.appendText(digito);
		textField.requestFocus();
		textField.positionCaret(textField.getLength());
		// Actualizar el cambio
		updateCambio();
	}

	/**
	 * Calcula y actualiza el cambio en el campo textCambio.
	 */
	private void updateCambio() {
		try {
			double efectivo = Double.parseDouble(textEfectivo.getText());
			double cambio = efectivo - totalVenta;
			textCambio.setText(String.valueOf(cambio));
		} catch (NumberFormatException e) {
			textCambio.clear();
		}
	}

	public void load(double totalVenta, ObservableList<VentaDetalleTabla> productos, String f) {
		this.totalVenta = totalVenta;
		this.productosVenta = productos;
		this.f = f;
		textTotal.setText(totalVenta + "");
	}

	@FXML
	void cobrarTotal(ActionEvent event) {
		try {
			// List<VentaDetalle> vd = this.productosVenta.
			if (textEfectivo.getText().isEmpty()) {
				throw new Exception("❌ Alerta: Debe ingresar un monto.");
			}
			double efectivo = Double.parseDouble(textEfectivo.getText());
			if (efectivo < totalVenta) {
				throw new Exception("❌ Alerta: El efectivo ingresado es menor al total de la venta.");
			}
			// Mostrar alerta de confirmación con el cambio (efectivo - totalVenta)
			double cambio = efectivo - totalVenta;
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Pago exitoso");
			alert.setHeaderText("Cambio: $" + cambio);
			alert.showAndWait();

			Stage stage = (Stage) btnCancelar.getScene().getWindow();
			stage.close();

			System.out.println(vs.saveVenta(f, Float.parseFloat(textTotal.getText()), this.productosVenta));

			vvm.cobrar();
			vvm.actualizarFolio();

		} catch (Exception e) {
			Alert infoAlert = new Alert(AlertType.WARNING);
			infoAlert.setTitle("Alerta");
			infoAlert.setHeaderText("Alerta");
			infoAlert.setContentText(e.getMessage() + " " + e.toString() + " " + e.getCause());
			infoAlert.showAndWait();
			System.out.println(e.getMessage() + " " + e.toString() + " " + e.getCause());
		}
	}

	@FXML
	void cerrar(ActionEvent event) {
		Stage stage = (Stage) btnCancelar.getScene().getWindow();
		stage.close();
	}

	@FXML
	void cantidadExacta(ActionEvent event) {
		try {
			// Asigna la cantidad exacta (total) al campo de efectivo.
			textEfectivo.setText(textTotal.getText());
			// Actualiza el cambio, que debería quedar en 0 si el efectivo es igual al
			// total.
			updateCambio();
			// Asegurarse de que, si se necesita, se marque que ya no es la primera edición.
			primeraEdicion = false;
		} catch (Exception e) {
			Alert infoAlert = new Alert(AlertType.ERROR);
			infoAlert.setTitle("Alerta");
			infoAlert.setHeaderText("algoun error en el cambio o cantidad igual");
			infoAlert.setContentText(e.getMessage());
			infoAlert.showAndWait();
		}
	}

	@FXML
	void limpiar(ActionEvent event) {
		textEfectivo.setText("");
		textCambio.setText("");
	}
}
