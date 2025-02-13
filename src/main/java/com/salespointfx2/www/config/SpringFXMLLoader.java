package com.salespointfx2.www.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javafx.fxml.FXMLLoader;

@Component
public class SpringFXMLLoader {
	private final ApplicationContext context;

	@Autowired
	public SpringFXMLLoader(ApplicationContext context) {
		this.context = context;
	}

	public FXMLLoader load(String fxmlPath) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
		loader.setControllerFactory(context::getBean); // Usar Spring para inyección
		return loader;
	}
}
