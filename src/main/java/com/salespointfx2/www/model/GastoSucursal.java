package com.salespointfx2.www.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "gastosucursal", uniqueConstraints = { @UniqueConstraint(columnNames = "idGastoSucursal") })
public class GastoSucursal {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(nullable = false)
	private Short idGastoSucursal;

	@Basic(optional = false)
	@Column(nullable = false, columnDefinition = "DATE")
	private LocalDate fechaGasto;

	@Basic(optional = false)
	@Column(nullable = false)
	private LocalDateTime createdAt;

	// @Max(value=?) @Min(value=?)//if you know range of your decimal fields
	// consider using these annotations to enforce field validation
	@Basic(optional = false)
	@Column(nullable = false)
	private float montoGasto;

	@JoinColumn(name = "gastoIdGastos", referencedColumnName = "idGastos")
	@ManyToOne(optional = false)
	private Gasto gastoIdGastos;

	@JoinColumn(name = "sucursalIdSucursal", referencedColumnName = "idSucursal")
	@ManyToOne(optional = false)
	private Sucursal sucursalIdSucursal;

	public GastoSucursal(LocalDate fechaGasto, float montoGasto, Gasto gastoIdGastos,
			Sucursal sucursalIdSucursal) {
		super();
		this.fechaGasto = fechaGasto;
		this.createdAt = LocalDateTime.now();
		this.montoGasto = montoGasto;
		this.gastoIdGastos = gastoIdGastos;
		this.sucursalIdSucursal = sucursalIdSucursal;
	}
	
	

}
