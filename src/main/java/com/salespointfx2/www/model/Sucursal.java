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
public class Sucursal {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	private Short idSucursal;

	@Basic(optional = false)
	@Column(nullable = false, length = 60, unique = true)
	private String nombreSucursal;

	@Basic(optional = false)
	@Column(nullable = false, length = 100, unique = true)
	private String calleSucursal;

	@Basic(optional = false)
	@Column(nullable = false, length = 60)
	private String ciudadSucursal;

	@Basic(optional = false)
	@Column(nullable = false, length = 30)
	private String estadoSucursal;

	@Basic(optional = true)
	@Column(nullable = false, length = 12)
	private String telefonoSucursal;

	@Basic(optional = false)
	private boolean estatusSucursal;

	@Basic(optional = false)
	private LocalDateTime createdAt;

	@Basic(optional = true)
	private LocalDateTime updatedAt;

	@Basic(optional = true)
	private LocalDateTime deletedAt;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "sucursalIdSucursal")
	private List<Venta> ventaList;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "sucursalIdSucursal")
	private List<GastoSucursal> gastoSucursalList;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "sucursalIdSucursal")
	private List<Movimiento> List;

	@OneToMany(mappedBy = "destinoIdSucursal")
	private List<Movimiento> movimientoList;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "sucursalIdSucursal")
	private List<SucursalProducto> sucursalFolioList;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "sucursalIdSucursal")
	private List<SucursalProducto> sucursalProductoList;

	@JoinColumn(name = "empresaIdEmpresa", referencedColumnName = "idEmpresa")
	@ManyToOne(optional = false)
	private Empresa empresaIdEmpresa;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "sucursalIdSucursal")
	private List<MovimientoCaja> movimientoCajaList;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "sucursalIdSucursal")
	private List<Folio> folios;

	public Sucursal(String nombreSucursal, String calleSucursal, String ciudadSucursal, String estadoSucursal, String telefonoSucursal, boolean estatusSucursal, Empresa empresaIdEmpresa) {
		super();
		this.nombreSucursal = nombreSucursal;
		this.calleSucursal = calleSucursal;
		this.ciudadSucursal = ciudadSucursal;
		this.estadoSucursal = estadoSucursal;
		this.telefonoSucursal = telefonoSucursal;
		this.estatusSucursal = estatusSucursal;
		this.empresaIdEmpresa = empresaIdEmpresa;
		this.createdAt = LocalDateTime.now();
	}

	public Sucursal(short idSucursal, String nombreSucursal) {
		super();
		this.idSucursal = idSucursal;
		this.nombreSucursal = nombreSucursal;
	}

	public Sucursal(short idSucursal) {
		super();
		this.idSucursal = idSucursal;
	}

}