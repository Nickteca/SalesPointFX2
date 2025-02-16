package com.salespointfx2.www.config;

import org.springframework.stereotype.Component;

import com.salespointfx2.www.dto.VentaDetalleTabla;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@Component
public class VentaViewModel {
	private final ObservableList<VentaDetalleTabla> productos = FXCollections.observableArrayList();
	private final DoubleProperty total = new SimpleDoubleProperty(0.0);

	public ObservableList<VentaDetalleTabla> getProductos() {
		return productos;
	}

	public DoubleProperty totalProperty() {
		return total;
	}

	public void cobrar() {
		productos.clear();
		total.set(0.0);
	}

	public void agregarProducto(VentaDetalleTabla producto) {
		productos.add(producto);
		calcularTotal();
	}

	public void calcularTotal() {
		double totalCalculado = productos.stream().mapToDouble(v -> v.getSubtotal().get()).sum();
		total.set(totalCalculado);
	}
	
}
