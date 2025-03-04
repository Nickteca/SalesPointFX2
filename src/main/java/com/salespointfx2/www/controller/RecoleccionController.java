package com.salespointfx2.www.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.salespointfx2.www.model.RecoleccionBillete;
import com.salespointfx2.www.service.RecoleccionService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyEvent;

@Component
public class RecoleccionController implements Initializable {
	@Autowired
	private RecoleccionService rs;

	private RecoleccionBillete rb;

	@FXML
	private Button btnRecoleccion;

	@FXML
	private Label lbl1;

	@FXML
	private Label lbl10;

	@FXML
	private Label lbl100;

	@FXML
	private Label lbl1000;

	@FXML
	private Label lbl2;

	@FXML
	private Label lbl20;

	@FXML
	private Label lbl200;

	@FXML
	private Label lbl5;

	@FXML
	private Label lbl50;

	@FXML
	private Label lbl500;
	@FXML
	private Label lblTotal;

	@FXML
	private TextField text1;

	@FXML
	private TextField text10;

	@FXML
	private TextField text100;

	@FXML
	private TextField text1000;

	@FXML
	private TextField text2;

	@FXML
	private TextField text20;

	@FXML
	private TextField text200;

	@FXML
	private TextField text5;

	@FXML
	private TextField text50;

	@FXML
	private TextField text500;

	// Método para calcular la suma total
	private void calcularTotal() {
		int total = 0;

		// Sumar todos los valores de los Labels de billetes y monedas
		total += obtenerValorLabel(lbl1);
		total += obtenerValorLabel(lbl2);
		total += obtenerValorLabel(lbl5);
		total += obtenerValorLabel(lbl10);
		total += obtenerValorLabel(lbl20);
		total += obtenerValorLabel(lbl50);
		total += obtenerValorLabel(lbl100);
		total += obtenerValorLabel(lbl200);
		total += obtenerValorLabel(lbl500);
		total += obtenerValorLabel(lbl1000);

		// Mostrar el total en lblTotal
		lblTotal.setText(String.valueOf(total));
	}

	@FXML
	void cantidad(KeyEvent event) {
		try {
			TextField text = (TextField) event.getSource();
			String textValue = text.getText();
			if (!textValue.isEmpty()) {
				// Convertir el valor a un número entero
				int value = Integer.parseInt(textValue);
				// Obtener el valor del ID del TextField (Ej: "text1000", "text500")
				String textFieldId = text.getId().replace("text", "");
				int multiplier = Integer.parseInt(textFieldId); // El valor del multiplicador es igual al ID sin "text"

				// Realizar el cálculo
				int result = value * multiplier;

				// Actualizar el Label correspondiente
				updateLabel(textFieldId, result);
				calcularTotal();
			} else { // Si está vacío, poner 0 en el Label correspondiente
				updateLabel(text.getId().replace("text", ""), 0);
				calcularTotal();
			}
		} catch (Exception e) {
			Alert error = new Alert(AlertType.ERROR);
			error.setTitle("Error en el keyrealese");
			error.setHeaderText("Parece que algo no esta funcionando");
			error.setContentText(e.getMessage() + "\n" + e.getCause());
			error.show();
		}
	}

	@FXML
	void guardarRecoleccion(ActionEvent event) {
		try {
			List<RecoleccionBillete> lrb = new ArrayList<>();
			for (TextField textField : new TextField[] { text1, text2, text5, text10, text20, text50, text100, text200, text500, text1000 }) {
				String id = textField.getId().replaceAll("text", ""); // Extraer el billete (ejemplo: "1000")
				int billete = Integer.parseInt(id);
				int cantidad = textField.getText().isEmpty() ? 0 : Integer.parseInt(textField.getText());
				int subtotal = billete * cantidad;

				if (cantidad > 0) { // Solo agregar si hay billetes
					lrb.add(new RecoleccionBillete((short) billete, (short) cantidad));
				}

			}
			if (rs.saveRecoleccion(lrb, Float.parseFloat(lblTotal.getText())) == null) {
				throw new Exception("no se registro");
			}
		} catch (Exception e) {
			Alert error = new Alert(AlertType.ERROR);
			error.setTitle("Error al ingresar recoleccion");
			error.setHeaderText("No se pudo ingresar el recoleccion in recoleecion billete");
			error.setContentText(e.getMessage() + "\n" + e.getCause());
			error.show();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		configurarTextFieldNumerico(text1);
		configurarTextFieldNumerico(text2);
		configurarTextFieldNumerico(text5);
		configurarTextFieldNumerico(text10);
		configurarTextFieldNumerico(text20);
		configurarTextFieldNumerico(text50);
		configurarTextFieldNumerico(text100);
		configurarTextFieldNumerico(text200);
		configurarTextFieldNumerico(text500);
		configurarTextFieldNumerico(text1000);

	}

	public void configurarTextFieldNumerico(TextField textField) {
		TextFormatter<String> formatter = new TextFormatter<>(change -> {
			if (change.getControlNewText().matches("\\d*")) { // Solo permite dígitos
				return change;
			}
			return null; // No acepta el cambio si no es un número
		});
		textField.setTextFormatter(formatter);
	}

	// Método auxiliar para obtener el valor de un Label
	private int obtenerValorLabel(Label label) {
		if (label != null && !label.getText().isEmpty()) {
			try {
				return Integer.parseInt(label.getText());
			} catch (NumberFormatException e) {
				return 0; // Si no es un número válido, devolver 0
			}
		}
		return 0;
	}

	private void updateLabel(String textFieldId, int result) {
		// Basado en el ID del TextField, actualizamos el Label correspondiente
		switch (textFieldId) {
		case "1":
			lbl1.setText(String.valueOf(result));
			break;
		case "2":
			lbl2.setText(String.valueOf(result));
			break;
		case "5":
			lbl5.setText(String.valueOf(result));
			break;
		case "10":
			lbl10.setText(String.valueOf(result));
			break;
		case "20":
			lbl20.setText(String.valueOf(result));
			break;
		case "50":
			lbl50.setText(String.valueOf(result));
			break;
		case "100":
			lbl100.setText(String.valueOf(result));
			break;
		case "200":
			lbl200.setText(String.valueOf(result));
			break;
		case "500":
			lbl500.setText(String.valueOf(result));
			break;
		case "1000":
			lbl1000.setText(String.valueOf(result));
			break;
		default:
			break;
		}
	}
}
