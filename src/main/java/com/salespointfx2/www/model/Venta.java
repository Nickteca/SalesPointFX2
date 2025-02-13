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
public class Venta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(nullable = false)
	private Integer idVenta;

	@Basic(optional = false)
	@Column(nullable = false, length = 17)
	private String folio;

	@Basic(optional = false)
	@Column(nullable = false)
	private boolean status;

	@Basic(optional = false)
	@Column(nullable = false, length = 1)
	private Character naturalezaVenta;

	// @Max(value=?) @Min(value=?)//if you know range of your decimal fields
	// consider using these annotations to enforce field validation
	@Basic(optional = false)
	@Column(nullable = false)
	private float totalVenta;

	@Basic(optional = false)
	@Column(nullable = false)
	private LocalDateTime createdAt;

	@Column(nullable = true)
	private LocalDateTime updatedAt;

	@Column(nullable = true)
	private LocalDateTime deletedAt;

	@JoinColumn(name = "sucursalIdSucursal", referencedColumnName = "idSucursal")
	@ManyToOne(optional = false)
	private Sucursal sucursalIdSucursal;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "ventaIdVenta")
	private List<VentaDetalle> ventaDetalleList;
}
