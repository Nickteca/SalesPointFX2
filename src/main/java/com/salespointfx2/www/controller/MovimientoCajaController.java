package com.salespointfx2.www.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.salespointfx2.www.config.SpringFXMLLoader;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

@Component
public class MovimientoCajaController {
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

	@FXML
	void abrirCaja(ActionEvent event) {

	}

	public Parent load() {
		Parent root;
		try {
			root = springFXMLLoader.load("/fxml/movimientocaja.fxml").load();
			return root;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}
}
