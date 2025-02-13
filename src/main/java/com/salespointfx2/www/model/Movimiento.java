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
public class Movimiento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(nullable = false)
	private Integer idMovimiento;

	@Basic(optional = false)
	@Column(nullable = false, length = 17)
	private String folio;

	@Basic(optional = false)
	@Column(nullable = false, length = 1)
	private Character naturalezaMovimiento;

	@Basic(optional = false)
	@Column(nullable = false)
	private LocalDateTime createdAt;

	@Column(nullable = true)
	private LocalDateTime updatedAt;

	@Column(nullable = true)
	private LocalDateTime deletedAt;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "movimientoIdMovimiento")
	private List<MovimientoDetalle> movimientoDetalleCollection;

	@JoinColumn(name = "sucursalIdSucursal", referencedColumnName = "idSucursal")
	@ManyToOne(optional = false)
	private Sucursal sucursalIdSucursal;

	@JoinColumn(name = "destinoIdSucursal", referencedColumnName = "idSucursal")
	@ManyToOne
	private Sucursal destinoIdSucursal;
}
