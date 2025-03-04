package com.salespointfx2.www.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import com.salespointfx2.www.config.SpringFXMLLoader;
import com.salespointfx2.www.service.TicketPrinter;

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
	@Autowired
	private TicketPrinter tps;

	private Stage currentStage;
	@FXML
	private BorderPane borderPanePrincipal;

	@FXML
	private MenuItem mItemAbrirCajon;

	@FXML
	private MenuItem mItemCerarCaja;

	@FXML
	private MenuItem mItemVentas;
	@FXML
	private MenuItem mItemInventario;
	@FXML
	private MenuItem mItemConsultaVenta;
	@FXML
	private MenuItem mItemPagoServicio;
	@FXML
	private MenuItem mItemRecoleccion;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	/* SE ABRE LA GABETA O CAJON DE DINERO */
	@FXML
	void abrirCajon(ActionEvent event) {

		tps.abririCajon();
	}

	/* CONSULTAMOS LA VENT DEL DIA O POR CORTE */
	@FXML
	void consultaVenta(ActionEvent event) {
		loadView("/fxml/ventacorte.fxml");
		VentaCorteController vcc = context.getBean(VentaCorteController.class);
	}

	/* ABRIREMOS LA VENTA INTERNA VENTA */
	@FXML
	void venta(ActionEvent event) {
		loadView("/fxml/venta.fxml");
		VentaController vc = context.getBean(VentaController.class);
		vc.load();

	}

	/* ABRIREMOS LA VENTA INTERNA INVENTARIO */
	@FXML
	void inventario(ActionEvent event) {
		loadView("/fxml/inventario.fxml");
		InventarioController ic = context.getBean(InventarioController.class);
		// vc.load();
	}

	@FXML
	void pagoServicio(ActionEvent event) {
		loadView("/fxml/gastos.fxml");
		GastoController ic = context.getBean(GastoController.class);
	}

	@FXML
	void recoleccion(ActionEvent event) {
		loadView("/fxml/recoleccion.fxml");
		RecoleccionController rc = context.getBean(RecoleccionController.class);
		// vc.load();
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
