package com.salespointfx2.www.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import com.salespointfx2.www.config.SpringFXMLLoader;
import com.salespointfx2.www.config.VentaViewModel;
import com.salespointfx2.www.dto.VentaDetalleTabla;
import com.salespointfx2.www.model.Categoria;
import com.salespointfx2.www.model.Folio;
import com.salespointfx2.www.model.SucursalProducto;
import com.salespointfx2.www.service.CategoriaService;
import com.salespointfx2.www.service.FolioService;
import com.salespointfx2.www.service.SucursalProductoService;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.input.KeyCode;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

@Component
public class VentaController implements Initializable, PropertyChangeListener {
	@Autowired
	private SpringFXMLLoader springFXMLLoader;
	@Autowired
	private ConfigurableApplicationContext context;
	@Autowired
	private SucursalProductoService sps;
	@Autowired
	private CategoriaService cs;
	@Autowired
	private VentaViewModel vvm;
	@Autowired
	private FolioService fs;

	@FXML
	private TabPane tPaneProductos;

	@FXML
	private TableView<VentaDetalleTabla> tablaVenta;
	@FXML
	private TableColumn<VentaDetalleTabla, Integer> colIdProducto;
	@FXML
	private TableColumn<VentaDetalleTabla, String> colProductoNombre;
	@FXML
	private TableColumn<VentaDetalleTabla, Float> colPrecio;
	@FXML
	private TableColumn<VentaDetalleTabla, Integer> colCantidad;
	@FXML
	private TableColumn<VentaDetalleTabla, Float> colSubTotal;

	@FXML
	private Label lblTotal;
	@FXML
	private Label lblFoliio;
	@FXML
	private Button btnCobrar;

	@FXML
	private Button btnEliminar;

	@FXML
	private Button btnLimpiar;

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
		// Configuración de las columnas
		colIdProducto.setCellValueFactory(cellData -> cellData.getValue().getIdProducto().asObject());
		colProductoNombre.setCellValueFactory(cellData -> cellData.getValue().getProducto());
		colCantidad.setCellValueFactory(cellData -> cellData.getValue().getCantidad().asObject());
		colPrecio.setCellValueFactory(cellData -> cellData.getValue().getPrecioUnitario().asObject());
		colSubTotal.setCellValueFactory(cellData -> cellData.getValue().getSubtotal().asObject());

		tablaVenta.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		tablaVenta.setItems(vvm.getProductos());
		lblTotal.textProperty().bind(vvm.totalProperty().asString("%.2f"));
		
		// Vincular lblFoliio a la propiedad folio del ViewModel
	    lblFoliio.textProperty().bind(vvm.folioProperty());
	    
		vvm.getProductos().clear();
		vvm.calcularTotal(); // Si recalcula el total según la lista, será 0
		vvm.actualizarFolio();

	}

	public void actualizarFolio() {
	    // Supón que fs.getFolioVenta() retorna un objeto Folio con la información actual.
	    Folio folioActual = fs.getFolioVenta();
	    // Formatea el folio según el formato deseado, por ejemplo: "VEN-11-1"
	    String folioFormateado = String.format("%s-%d-%d",
	            folioActual.getAcronimoFolio().replace("-", ""),
	            folioActual.getSucursalIdSucursal().getIdSucursal(),
	            folioActual.getNumeroFolio());
	    // Actualiza la propiedad en el ViewModel
	    vvm.setFolio(folioFormateado);
	    lblFoliio.setUserData(folioActual);
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
					cantidadSatge("/fxml/cantidad.fxml", Integer.parseInt(btn.getId()));
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
				cantidadSatge("/fxml/cantidad.fxml", Integer.parseInt(btn.getId()));

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

	public void cantidadSatge(String fxmlPath, int idProducto) {
		try {
			FXMLLoader fxml = new FXMLLoader(getClass().getResource(fxmlPath));
			Parent view = fxml.load();

			CantidadController cc = fxml.getController();
			cc.addPropertyChangeListener(this);
			cc.setProductoId(idProducto);

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
		btnCobrar.getScene().setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.F12) {
				event.consume(); // Evitar propagación del evento
				btnCobrar.fire(); // Ejecutar el botón de prueba siempre
			}
		});
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if ("cantidadSeleccionada".equals(evt.getPropertyName())) {
			Object[] values = (Object[]) evt.getNewValue();
			int productoId = (int) values[0];
			int cantidad = (int) values[1];

			// Aquí puedes usar el productoId y la cantidad para realizar la consulta
			System.out.println("ID del producto: " + productoId + ", Cantidad: " + cantidad);

			// Consultar la base de datos con el ID del producto
			SucursalProducto producto = sps.getSucursalProductoById(productoId);

			// Realiza la acción deseada, como agregar el producto a la venta
			agregarProductoAVenta(producto, cantidad);
		}

	}

	public void agregarProductoAVenta(SucursalProducto producto, int cantidad) {

		// Verificar si el producto ya está en la tabla
		VentaDetalleTabla ventaExistente = tablaVenta.getItems().stream()
				.filter(v -> v.getProducto().get().equals(producto.getProductoIdProducto().getNombreProducto()))
				.findFirst().orElse(null);

		if (ventaExistente != null) {
			// Si el producto ya está en la tabla, aumentar la cantidad
			int nuevaCantidad = cantidad;
			ventaExistente.setCantidad(nuevaCantidad);
			vvm.calcularTotal();
		} else {
			// Si no está, agregar un nuevo registro
			VentaDetalleTabla nuevaVenta = new VentaDetalleTabla(
					new SimpleIntegerProperty(producto.getProductoIdProducto().getIdProducto()), // idProducto
					new SimpleStringProperty(producto.getProductoIdProducto().getNombreProducto()), // Nombre del
																									// producto
					new SimpleIntegerProperty(cantidad), // Cantidad inicial
					new SimpleFloatProperty(producto.getPrecio()), // Precio unitario
					new SimpleFloatProperty(producto.getPrecio() * cantidad) // Subtotal (cantidad * precio)
			);
			// tableVentaDetalle.getItems().add(nuevaVenta);
			vvm.agregarProducto(nuevaVenta); // Agregar el producto al ViewModel
			tablaVenta.scrollTo(nuevaVenta);
			tablaVenta.getSelectionModel().select(nuevaVenta);
		}
		// tablaVenta.refresh();
	}

	@FXML
	void cobrar(ActionEvent event) {
		try {
			if (tablaVenta.getItems().isEmpty()) {
				throw new Exception("❌ Alerta: La tabla está vacía.");
			}

			// Obtener el total de la venta (verificar si es un número)
			double totalVenta;
			try {
				totalVenta = Double.parseDouble(lblTotal.getText().replace("$", "").trim());
			} catch (NumberFormatException e) {
				throw new Exception("❌ Alerta: Total inválido.");
			}
			// springFXMLLoader.load(fxmlPath);
			FXMLLoader loader = springFXMLLoader.load("/fxml/cambio.fxml");
			Parent root = loader.load();

			// Obtener el controlador y pasar los datos necesarios
			CambioController contro = loader.getController();
			contro.load(totalVenta, tablaVenta.getItems(), lblFoliio.getText());
			//System.out.println("Los que le estamos mndando des: "+tablaVenta.getItems());

			/*
			 * CambioController controlador = loader.getController();
			 * controlador.load(totalVenta, tablaVenta.getItems());
			 */

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL); // Hace que la ventana sea modal
			stage.initStyle(StageStyle.DECORATED); // Puedes cambiarlo a UNDECORATED si prefieres sin bordes
			stage.setTitle("Cobrando");
			stage.setScene(new Scene(root));
			stage.setResizable(false);
			stage.showAndWait(); // Espera hasta que el modal se cierre
		} catch (Exception e) {
			Alert infoAlert = new Alert(AlertType.WARNING);
			infoAlert.setTitle("Alerta");
			infoAlert.setHeaderText("Alerta");
			infoAlert.setContentText(e.getMessage());
			infoAlert.showAndWait();
		}
	}

	@FXML
	void eliminarProducto(ActionEvent event) {
		VentaDetalleTabla productoSeleccionado = tablaVenta.getSelectionModel().getSelectedItem();

		if (productoSeleccionado != null) {
			vvm.eliminarProducto(productoSeleccionado);
		} else {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Atención");
			alert.setHeaderText("No se ha seleccionado ningún producto");
			alert.setContentText("Por favor, selecciona un producto para eliminar.");
			alert.showAndWait();
		}
	}

	@FXML
	void limpiarProductos(ActionEvent event) {
		vvm.getProductos().clear();
		vvm.calcularTotal(); // Si recalcula el total según la lista, será 0
	}

}
