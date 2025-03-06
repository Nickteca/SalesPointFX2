package com.salespointfx2.www;

import java.io.IOException;
import java.net.ServerSocket;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.salespointfx2.www.controller.MovimientoCajaController;
import com.salespointfx2.www.controller.StarterController;
import com.salespointfx2.www.service.MovimientoCajaService;
import com.salespointfx2.www.service.SucursalService;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

@SpringBootApplication
public class SalesPointFx2Application extends Application {
	// ESTA VARIABLE NOS ARROJARA TODOS LOS BEANS O LOS CARGARA PARA PODER
	// INJECTARLOS
	private static ConfigurableApplicationContext context;
	private static MovimientoCajaService mcs;
	private static SucursalService ss;

	private static ServerSocket lockSocket;

	public static void main(String[] args) {
		if (!isAlreadyRunning()) {
			context = SpringApplication.run(SalesPointFx2Application.class, args);
			mcs = context.getBean(MovimientoCajaService.class);
			ss = context.getBean(SucursalService.class);
			launch();
		} else {
			Platform.runLater(() -> {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("La aplicación ya está en ejecución");
				alert.setContentText("Solo se permite una instancia de la aplicación.");
				alert.showAndWait();
				Platform.exit(); // Cierra la aplicación
			});
		}
	}

	private static boolean isAlreadyRunning() {
		try {
			lockSocket = new ServerSocket(9999); // Usa un puerto único
			return false;
		} catch (IOException e) {
			return true;
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		// EN LUGAR DE USAR LOS IF SE USA ESTE TIPO DE OPERACION POR SER OPTIONAL<>
		ss.getSucursalActive().flatMap(sucursal -> mcs.getLastMovimientoCaja(Optional.of(sucursal))) // Pasamos un
																										// Optional<Sucursal>
				/*
				 * .filter(mc -> mc.getTipoMovimientoCaja() == 'A') // Filtramos según el tipo
				 * de movimiento .filter(mc -> mc.getCreatedAt().isBefore(LocalDateTime.now()))
				 */
				.ifPresentOrElse(mc -> {
					if (mc.getTipoMovimientoCaja() == 'A') {
						if (mc.getCreatedAt().isBefore(LocalDateTime.now())) {
							mostrarPrincipal(primaryStage);
						} else {
							Alert info = new Alert(AlertType.WARNING);
							info.setTitle("SalespintFx Warning");
							info.setHeaderText("Al parecer no coincide la fecha");
							info.setContentText("Revisa la fecha. no esta coincidiendo con la apertura de la caja");
							info.show();
						}
					} else {
						abiriCaja();
					}

				}, () -> {
					// Si el tipo de movimiento no es 'A', realizamos una acción alternativa
					Alert alerta = new Alert(AlertType.WARNING);
					alerta.setTitle("Salespointfx2 Warning");
					alerta.setHeaderText("Al parecer es null el mocimiento de caja");
					alerta.setContentText("");
					alerta.showAndWait();
					abiriCaja();
					// Aquí puedes agregar la acción alternativa, por ejemplo:
					// primaryStage.close(); o alguna otra lógica que desees.
				});
	}

	public void mostrarPrincipal(Stage primaryStage) {
		try {
			StarterController sc = context.getBean(StarterController.class);
			Parent starter = sc.load();
			primaryStage = new Stage();
			primaryStage.setScene(new Scene(starter));
			// primaryStage.setMaximized(true);
			primaryStage.setTitle("SalespointFx");
			primaryStage.setMinWidth(1024); // Mínimo ancho permitido
			primaryStage.setMinHeight(768); // Mínimo alto permitido
			primaryStage.showAndWait();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void abiriCaja() {
		MovimientoCajaController mcc = context.getBean(MovimientoCajaController.class);
		Parent abrirCaja = mcc.load(ss.getSucursalActive().get().getNombreSucursal(), "Abriri Caja");
		Stage abrirCajaStage = new Stage();
		abrirCajaStage.setScene(new Scene(abrirCaja, 600, 400));
		abrirCajaStage.setResizable(false);
		abrirCajaStage.showAndWait(); // Espera a que se seleccione una sucursal

	}

}
