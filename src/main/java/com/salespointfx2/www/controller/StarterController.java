package com.salespointfx2.www.controller;

import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;

@Component
public class StarterController {
	@FXML
	private BorderPane anchorPanePrincipal;

	@FXML
	private MenuItem mItemAbrirCajon;

	@FXML
	private MenuItem mItemCerrarCaja;

	@FXML
	private MenuItem mItemVenta;
}
