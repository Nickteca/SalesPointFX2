package com.salespointfx2.www.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VentaCorteDTO {
	private int idSucursalProducto;
	private String nombreProducto;
	private int unidades;
	private float precio;
	private float subtotal;
}
