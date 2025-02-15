package com.salespointfx2.www.dto;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VentaDetalleTabla {
	private final SimpleIntegerProperty idProducto;
	private final SimpleStringProperty producto;
	private final SimpleIntegerProperty cantidad;
	private final SimpleFloatProperty precioUnitario;
	private final SimpleFloatProperty subtotal;

	// Método para actualizar el subtotal cuando cambia la cantidad
	public void setCantidad(int cantidad) {
		this.cantidad.set(cantidad);
		this.subtotal.set(cantidad * precioUnitario.get());
	}
}
