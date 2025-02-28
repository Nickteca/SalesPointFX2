package com.salespointfx2.www.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GastoSucursalDTO {
	private int idGastoSucursal;
	private String descripcionGasto;
	private float monto;
	private LocalDate fecha;
}
