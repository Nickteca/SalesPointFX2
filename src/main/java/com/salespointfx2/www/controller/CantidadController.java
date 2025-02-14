package com.salespointfx2.www.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

@Component
public class CantidadController implements Initializable {
	@FXML
	private Button btn0;

	@FXML
	private Button btn1;

	@FXML
	private Button btn2;

	@FXML
	private Button btn3;

	@FXML
	private Button btn4;

	@FXML
	private Button btn5;

	@FXML
	private Button btn6;

	@FXML
	private Button btn7;

	@FXML
	private Button btn8;

	@FXML
	private Button btn9;

	@FXML
	private Button btnAceptar;

	@FXML
	private Button btnCancelar;

	@FXML
	private TextField textCantidad;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Analizando");
		alert.setHeaderText("Este es el initialized");
		alert.showAndWait();
	}

}
