package com.salespointfx2.www.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventarioDetalleDTO {
	private int idSucursalProducto;
	private String nombreProducto;
	private String categoria;
	private float inventario;
	private float precio;
}
