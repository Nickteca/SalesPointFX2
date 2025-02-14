package com.salespointfx2.www.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.salespointfx2.www.config.SpringFXMLLoader;
import com.salespointfx2.www.model.Sucursal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

@Component
public class MovimientoCajaController implements Initializable {
	@Autowired
	private SpringFXMLLoader springFXMLLoader;

	@FXML
	private Button btnCancelar;

	@FXML
	private Button btnGuardar;

	@FXML
	private Label lblSubtitulo;

	@FXML
	private Label lblTitulo;

	@FXML
	private TextField textEfectivo;

	private Sucursal sucursal;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@FXML
	void abrirCaja(ActionEvent event) {

	}

	public Parent load(String titulo, String subtitulo) {
		Parent root;
		try {
			root = springFXMLLoader.load("/fxml/movimientocaja.fxml").load();
			this.lblTitulo.setText(titulo);
			this.lblSubtitulo.setText(subtitulo);
			return root;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

}
