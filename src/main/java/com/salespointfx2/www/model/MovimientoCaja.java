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
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(indexes = { @Index(name = "idx_fecha_movimiento", columnList = "createdAt") })
public class MovimientoCaja {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(nullable = false)
	private Integer idMovimientoCaja;

	@Basic(optional = false)
	@Column(nullable = false, length = 1)
	private Character tipoMovimientoCaja;

	// @Max(value=?) @Min(value=?)//if you know range of your decimal fields
	// consider using these annotations to enforce field validation
	@Basic(optional = false)
	@Column(nullable = false)
	private float saldoInicial;

	@Basic(optional = false)
	@Column(nullable = false)
	private LocalDateTime createdAt;

	@JoinColumn(name = "sucursalIdSucursal", referencedColumnName = "idSucursal")
	@ManyToOne(optional = false)
	private Sucursal sucursalIdSucursal;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "movimientoCajaIdMovimientoCaja")
	private List<Corte> corteList;

	public MovimientoCaja(Character tipoMovimientoCaja, float saldoInicial, Sucursal sucursalIdSucursal) {
		super();
		this.tipoMovimientoCaja = tipoMovimientoCaja;
		this.saldoInicial = saldoInicial;
		this.sucursalIdSucursal = sucursalIdSucursal;
		this.createdAt = LocalDateTime.now();
		;
	}
}