package com.salespointfx2.www.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import com.salespointfx2.www.config.SpringFXMLLoader;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

@Component
public class StarterController implements Initializable {
	@Autowired
	private SpringFXMLLoader springFXMLLoader;
	@Autowired
	private ConfigurableApplicationContext context;

	private Stage currentStage;
	@FXML
	private BorderPane borderPanePrincipal;

	@FXML
	private MenuItem mItemAbrirCajon;

	@FXML
	private MenuItem mItemCerarCaja;

	@FXML
	private MenuItem mItemVentas;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@FXML
	void venta(ActionEvent event) {
		loadView("/fxml/venta.fxml");
		
	}

	public Parent load() {
		Parent root;
		try {
			root = springFXMLLoader.load("/fxml/starter.fxml").load();
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

	public void loadView(String fxmlPath) {
		try {
			FXMLLoader fxml = springFXMLLoader.load(fxmlPath);
			AnchorPane view = fxml.load();
			// BorderPane borderPane = (BorderPane) currentStage.getScene().getRoot();
			borderPanePrincipal.setCenter(view);
			VentaController vc = context.getBean(VentaController.class);
			vc.load();
			
		} catch (IOException e) {
			e.printStackTrace();
			showErrorDialog("Error al cargar la vista", e.getMessage());
		}
	}

	private void showErrorDialog(String title, String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(message);
		alert.showAndWait();
	}
}
