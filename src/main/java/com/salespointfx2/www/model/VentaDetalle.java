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
public class VentaDetalle {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(nullable = false)
	private Integer idVentaDetalle;

	@Basic(optional = false)
	@Column(nullable = false)
	private short cantidad;
	// @Max(value=?) @Min(value=?)//if you know range of your decimal fields
	// consider using these annotations to enforce field validation
	@Basic(optional = false)
	@Column(nullable = false)
	private float precio;

	@Basic(optional = false)
	@Column(nullable = false)
	private float subTotal;

	@JoinColumn(name = "sucursalProductoIdSucursalProducto", referencedColumnName = "idSucursalProducto")
	@ManyToOne(optional = false)
	private SucursalProducto sucursalProductoIdSucursalProducto;

	@JoinColumn(name = "ventaIdVenta", referencedColumnName = "idVenta")
	@ManyToOne(optional = false)
	private Venta ventaIdVenta;

	// Evento JPA para garantizar el cálculo antes de guardar
	/*
	 * @PrePersist
	 * 
	 * @PreUpdate private void calcularAntesDeGuardar() { if (precio != null &&
	 * cantidad != null) { this.subTotal = this.precio * this.cantidad; } else {
	 * this.subTotal = 0; } }
	 */
}
