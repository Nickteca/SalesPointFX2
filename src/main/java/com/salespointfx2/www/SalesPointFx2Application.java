package com.salespointfx2.www;

import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.salespointfx2.www.controller.MovimientoCajaController;
import com.salespointfx2.www.controller.StarterController;
import com.salespointfx2.www.service.MovimientoCajaService;
import com.salespointfx2.www.service.SucursalService;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

@SpringBootApplication
public class SalesPointFx2Application extends Application {
	// ESTA VARIABLE NOS ARROJARA TODOS LOS BEANS O LOS CARGARA PARA PODER
	// INJECTARLOS
	private static ConfigurableApplicationContext context;
	private static MovimientoCajaService mcs;
	private static SucursalService ss;

	public static void main(String[] args) {
		context = SpringApplication.run(SalesPointFx2Application.class, args);
		mcs = context.getBean(MovimientoCajaService.class);
		ss = context.getBean(SucursalService.class);
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		// EN LUGAR DE USAR LOS IF SE USA ESTE TIPO DE OPERACION POR SER OPTIONAL<>
		ss.getSucursalActive().flatMap(sucursal -> mcs.getLastMovimientoCaja(Optional.of(sucursal))) // Pasamos un
																										// Optional<Sucursal>
				.filter(mc -> mc.getTipoMovimientoCaja() == 'A') // Filtramos según el tipo de movimiento
				.ifPresentOrElse(mc -> {
					// Si el tipo de movimiento es 'A', mostramos la ventana
					mostrarPrincipal(primaryStage);

				}, () -> {
					// Si el tipo de movimiento no es 'A', realizamos una acción alternativa
					System.out.println("El tipo de movimiento no es 'A'. Realizando otra acción.");
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
			//primaryStage.setMaximized(true);
			primaryStage.setTitle("SalespointFx");
			primaryStage.setMinWidth(1024);  // Mínimo ancho permitido
			primaryStage.setMinHeight(768); // Mínimo alto permitido
			primaryStage.showAndWait();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void abiriCaja() {
		MovimientoCajaController mcc = context.getBean(MovimientoCajaController.class);
		Parent abrirCaja = mcc.load(ss.getSucursalActive().get().getNombreSucursal(), "Cerrar Caja");
		Stage abrirCajaStage = new Stage();
		abrirCajaStage.setScene(new Scene(abrirCaja, 600, 400));
		abrirCajaStage.setResizable(false);
		abrirCajaStage.showAndWait(); // Espera a que se seleccione una sucursal

	}

}
