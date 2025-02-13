package com.salespointfx2.www.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Empresa {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	private Short idEmpresa;

	@Basic(optional = false)
	@Column(length = 50, nullable = false, unique = true)
	private String nombreEmpresa;

	@Basic(optional = false)
	@Column(nullable = false, length = 100)
	private String direccionEmpresa;

	@Basic(optional = false)
	@Column(nullable = false, length = 12)
	private String telefonoEmpresa;

	@Basic(optional = false)
	@Column(nullable = false, length = 15)
	private String rfcEmpresa;

	@Basic(optional = false)
	@Column(nullable = true)
	private LocalDateTime createdAt;

	@Basic(optional = true)
	@Column(nullable = true)
	private LocalDateTime updatedAt;

	@Basic(optional = true)
	@Column(nullable = true)
	private LocalDateTime deletedAt;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "empresaIdEmpresa")
	private List<Sucursal> sucursalCollection;

	public Empresa(String nombreEmpresa, String direccionEmpresa, String telefonoEmpresa, String rfcEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
		this.direccionEmpresa = direccionEmpresa;
		this.telefonoEmpresa = telefonoEmpresa;
		this.rfcEmpresa = rfcEmpresa;
		this.createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
		;
	}
}
