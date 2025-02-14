package com.salespointfx2.www.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import com.salespointfx2.www.config.SpringFXMLLoader;
import com.salespointfx2.www.model.Categoria;
import com.salespointfx2.www.model.SucursalProducto;
import com.salespointfx2.www.service.CategoriaService;
import com.salespointfx2.www.service.SucursalProductoService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

@Component
public class VentaController implements Initializable {
	@Autowired
	private SpringFXMLLoader springFXMLLoader;
	@Autowired
	private ConfigurableApplicationContext context;
	@Autowired
	private SucursalProductoService sps;
	@Autowired
	private CategoriaService cs;
	@FXML
	private Button btnPrueba;

	@FXML
	private TabPane tPaneProductos;

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
		getAllProductosVendibles();
		getProductosXCategoria();
	}

	public void getProductosXCategoria() {
		List<Categoria> lc = cs.getAllCategorias();
		for (Categoria categoria : lc) {
			List<SucursalProducto> lsp = sps.getAllProductosVendibleXCategpria(categoria);

			GridPane gp = new GridPane();
			gp.setPadding(new Insets(10)); // Espaciado alrededor de la cuadrícula
			gp.setHgap(5); // Espaciado horizontal entre columnas
			gp.setVgap(5); // Espaciado vertical entre filas
			gp.setAlignment(Pos.CENTER);

			// Configurar restricciones de columna y fila
			ColumnConstraints columnConstraints = new ColumnConstraints();
			columnConstraints.setHgrow(Priority.ALWAYS); // Permitir que las columnas crezcan
			columnConstraints.setFillWidth(true); // Llenar todo el ancho de la columna

			RowConstraints rowConstraints = new RowConstraints();
			rowConstraints.setVgrow(Priority.ALWAYS); // Permitir que las filas crezcan
			rowConstraints.setFillHeight(true); // Llenar toda la altura de la fila

			int numColumns = 3; // Número de columnas en la cuadrícula

			for (int j = 0; j < numColumns; j++) {
				gp.getColumnConstraints().add(columnConstraints);
			}

			for (int j = 0; j <= lsp.size() / numColumns; j++) {
				gp.getRowConstraints().add(rowConstraints);
			}

			for (int i = 0; i < lsp.size(); i++) {
				String producto = lsp.get(i).getProductoIdProducto().getNombreProducto();
				Button btn = new Button(producto);
				btn.setId(lsp.get(i).getIdSucursalProducto() + "");
				btn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); // Ajustar al tamaño máximo de la celda
				btn.setOnAction(event -> {

					/* mostrarModalCantidad(Integer.parseInt(btn.getId())); */
					cantidadSatge("/fxml/cantidad.fxml");
					CantidadController cc = context.getBean(CantidadController.class);
				});

				// Crear el tooltip y configurarlo
				Tooltip tooltip = new Tooltip("Precio: " + lsp.get(i).getPrecio());
				Tooltip.install(btn, tooltip); // Asociar el tooltip al botón

				// Calcular fila y columna
				int row = i / numColumns;
				int col = i % numColumns;
				// Habilitar ajuste de texto
				btn.setWrapText(true);
				btn.getStyleClass().add("botonesProductos");

				// Agregar el botón al GridPane
				gp.add(btn, col, row);
			}
			ScrollPane scrollPane = new ScrollPane(gp);
			scrollPane.setFitToWidth(true); // Ajustar el ancho del contenido al tamaño del ScrollPane

			Tab tab = new Tab();
			StackPane sp = new StackPane(scrollPane);
			sp.setId(categoria.getNombreCategoria());
			tab.setText(categoria.getNombreCategoria());
			tab.setContent(sp);
			tPaneProductos.getTabs().add(tab);
		}
	}

	public void getAllProductosVendibles() {
		List<SucursalProducto> lsp = sps.getAllProductosVendibles();
		GridPane gp = new GridPane();
		gp.setPadding(new Insets(10)); // Espaciado alrededor de la cuadrícula
		gp.setHgap(5); // Espaciado horizontal entre columnas
		gp.setVgap(5); // Espaciado vertical entre filas
		gp.setAlignment(Pos.CENTER);
		// Configurar restricciones de columna y fila
		ColumnConstraints columnConstraints = new ColumnConstraints();
		columnConstraints.setHgrow(Priority.ALWAYS); // Permitir que las columnas crezcan
		columnConstraints.setFillWidth(true); // Llenar todo el ancho de la columna

		RowConstraints rowConstraints = new RowConstraints();
		rowConstraints.setVgrow(Priority.ALWAYS); // Permitir que las filas crezcan
		rowConstraints.setFillHeight(true); // Llenar toda la altura de la fila

		int numColumns = 3; // Número de columnas en la cuadrícula

		for (int j = 0; j < numColumns; j++) {
			gp.getColumnConstraints().add(columnConstraints);
		}

		for (int j = 0; j <= lsp.size() / numColumns; j++) {
			gp.getRowConstraints().add(rowConstraints);
		}
		for (int i = 0; i < lsp.size(); i++) {
			String producto = lsp.get(i).getProductoIdProducto().getNombreProducto();
			Button btn = new Button(producto);
			btn.setId(lsp.get(i).getIdSucursalProducto() + "");
			btn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); // Ajustar al tamaño máximo de la celda
			/* agregamos eventos a los botones com oescuchadoes */
			btn.setOnAction(event -> {
				/* mostrarModalCantidad(Integer.parseInt(btn.getId())); */
				cantidadSatge("/fxml/cantidad.fxml");
				CantidadController cc = context.getBean(CantidadController.class);
			});
			// btn.setPrefWidth(120); // Ancho preferido del botón
			// btn.setPrefHeight(50); // Alto preferido del botón

			// Crear el tooltip y configurarlo
			Tooltip tooltip = new Tooltip("Precio: " + lsp.get(i).getPrecio());
			Tooltip.install(btn, tooltip); // Asociar el tooltip al botón

			// Calcular fila y columna
			int row = i / numColumns;
			int col = i % numColumns;
			// Habilitar ajuste de texto
			btn.setWrapText(true);
			// Agregamos estilos
			btn.getStyleClass().add("botonesProductos");

			// Agregar el botón al GridPane
			gp.add(btn, col, row);
		}
		ScrollPane scrollPane = new ScrollPane(gp);
		scrollPane.setFitToWidth(true); // Ajustar el ancho del contenido al tamaño del ScrollPane

		Tab tab = new Tab();
		StackPane sp = new StackPane(scrollPane);
		sp.setId("TODOS");
		tab.setText("TODOS");
		tab.setContent(sp);
		tPaneProductos.getTabs().add(tab);
	}

	public void cantidadSatge(String fxmlPath) {
		try {
			FXMLLoader fxml = springFXMLLoader.load(fxmlPath);
			Parent view = fxml.load();
			Stage modal = new Stage();
			modal.initModality(Modality.APPLICATION_MODAL);
			modal.setScene(new Scene(view));
			modal.showAndWait(); // Bloquea hasta que la ventana se cierre

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

	public void load() {

	}

}
