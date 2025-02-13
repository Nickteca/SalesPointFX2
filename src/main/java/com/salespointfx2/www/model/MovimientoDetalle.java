package com.salespointfx2.www.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovimientoDetalle {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(nullable = false)
	private Integer idMovimientoDetalle;

	@Basic(optional = false)
	@Column(nullable = false)
	private short unidades;

	@JoinColumn(name = "movimientoIdMovimiento", referencedColumnName = "idMovimiento")
	@ManyToOne(optional = false)
	private Movimiento movimientoIdMovimiento;

	@JoinColumn(name = "sucursalProductoIdSucursalProducto", referencedColumnName = "idSucursalProducto")
	@ManyToOne(optional = false)
	private SucursalProducto sucursalProductoIdSucursalProducto;
}