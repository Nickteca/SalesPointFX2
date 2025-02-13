package com.salespointfx2.www;

import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.salespointfx2.www.controller.MovimientoCajaController;
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

	public static void main(String[] args) {
		context = SpringApplication.run(SalesPointFx2Application.class, args);
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		MovimientoCajaService mcs = context.getBean(MovimientoCajaService.class);
		SucursalService ss = context.getBean(SucursalService.class);
		// EN LUGAR DE USAR LOS IF SE USA ESTE TIPO DE OPERACION POR SER OPTIONAL<>
		ss.getSucursalActive().flatMap(sucursal -> mcs.getLastMovimientoCaja(Optional.of(sucursal))) // Pasamos un Optional<Sucursal>
				.filter(mc -> mc.getTipoMovimientoCaja() == 'A') // Filtramos según el tipo de movimiento
				.ifPresentOrElse(mc -> {
					// Si el tipo de movimiento es 'A', mostramos la ventana

				}, () -> {
					// Si el tipo de movimiento no es 'A', realizamos una acción alternativa
					System.out.println("El tipo de movimiento no es 'A'. Realizando otra acción.");
					abiriCaja();
					// Aquí puedes agregar la acción alternativa, por ejemplo:
					// primaryStage.close(); o alguna otra lógica que desees.
				});
	}

	public void mostrarPrincipal() {

	}

	public void abiriCaja() {
		MovimientoCajaController mcc = context.getBean(MovimientoCajaController.class);
		Parent abrirCaja = mcc.load();
		Stage abrirCajaStage = new Stage();
		abrirCajaStage.setScene(new Scene(abrirCaja, 600, 400));
		abrirCajaStage.setResizable(false);
		abrirCajaStage.showAndWait(); // Espera a que se seleccione una sucursal

	}

}
