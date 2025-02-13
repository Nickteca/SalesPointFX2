package com.salespointfx2.www.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Corte {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(nullable = false)
	private Integer idCorte;

	@Basic(optional = false)
	@Column(nullable = false)
	private LocalDateTime fechaAperturaCorte;

	// @Max(value=?) @Min(value=?)//if you know range of your decimal fields
	// consider using these annotations to enforce field validation
	@Basic(optional = false)
	@Column(nullable = false)
	private float saldoInicial;

	@Basic(optional = false)
	@Column(nullable = false)
	private float conteoFinal;

	@Basic(optional = false)
	@Column(nullable = false)
	private float venta;

	@Basic(optional = false)
	@Column(nullable = false)
	private float totalGastos;

	@Basic(optional = false)
	@Column(nullable = false)
	private float faltante;

	@Basic(optional = false)
	@Column(nullable = false)
	private short numFolios;

	@Column(nullable = true)
	private String folioInicio;

	@Column(nullable = true)
	private String folioFinal;

	@Basic(optional = false)
	@Column(nullable = false)
	private LocalDateTime fechaCierre;

	@JoinColumn(name = "movimientoCajaIdMovimientoCaja", referencedColumnName = "idMovimientoCaja")
	@ManyToOne(optional = false)
	private MovimientoCaja movimientoCajaIdMovimientoCaja;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "corteIdCorte")
	private List<CorteDetalle> corteDetalleList;
}
