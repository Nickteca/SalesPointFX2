package com.salespointfx2.www.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.salespointfx2.www.config.SpringFXMLLoader;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;

@Component
public class StarterController implements Initializable {
	@Autowired
	private SpringFXMLLoader springFXMLLoader;
	@FXML
	private BorderPane anchorPanePrincipal;

	@FXML
	private MenuItem mItemAbrirCajon;

	@FXML
	private MenuItem mItemCerrarCaja;

	@FXML
	private MenuItem mItemVenta;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	public Parent load() {
		Parent root;
		try {
			root = springFXMLLoader.load("/fxml/starter.fxml").load();
			return root;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
