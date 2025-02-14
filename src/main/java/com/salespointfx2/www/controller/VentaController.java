package com.salespointfx2.www.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.salespointfx2.www.config.SpringFXMLLoader;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;

@Component
public class VentaController implements Initializable {
	@Autowired
	private SpringFXMLLoader springFXMLLoader;
	@FXML
	private Button btnPrueba;

	@FXML
	void guardarVenta(ActionEvent event) {
		Alert infoAlert = new Alert(AlertType.CONFIRMATION);
		infoAlert.setTitle("Confirmacion");
		infoAlert.setHeaderText("Funciona el controlador");
		infoAlert.setContentText("Esta bien");
		infoAlert.showAndWait();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println(resources+" "+location);
	}

	public Parent load() {
		Parent root;
		try {
			root = springFXMLLoader.load("/fxml/venta.fxml").load();
			return root;
		} catch (IOException e) {
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Error de Carga");
	        alert.setHeaderText("No se pudo cargar la vista.");
	        alert.setContentText("Hubo un error al cargar la vista. Por favor, intente de nuevo.");
	        alert.showAndWait();
	        e.printStackTrace();
	        return null;
		}
	}

}
