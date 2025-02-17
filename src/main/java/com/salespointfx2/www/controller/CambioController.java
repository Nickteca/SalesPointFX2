package com.salespointfx2.www.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import com.salespointfx2.www.dto.VentaDetalleTabla;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;

@Component
public class CambioController implements Initializable {
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

	private double totalVenta;
	private ObservableList<VentaDetalleTabla> productosVenta;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	public void load(double totalVenta, ObservableList<VentaDetalleTabla> productos) {
		this.totalVenta = totalVenta;
		this.productosVenta = productos;
		textTotal.setText(totalVenta + "");
		btnCobrar.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.F12) {
				event.consume(); // Evitar propagación del evento
				btnCobrar.fire(); // Ejecutar el botón de prueba siempre
			}
		});

	}

	@FXML
	void cobrarTotal(ActionEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("entro!!!");
		alert.setHeaderText("Al parecer funciona con la tecla f12");
		alert.showAndWait();
	}
}
