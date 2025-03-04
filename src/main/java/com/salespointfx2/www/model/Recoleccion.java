package com.salespointfx2.www.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

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
@Table(indexes = { @Index(name = "creado", columnList = "createdAt") })
public class Recoleccion implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, unique = true)
	private int idRecoleccion;

	@Column(nullable = false)
	private double totalRecoleccion;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	@JoinColumn(name = "sucursalIdSucursal", referencedColumnName = "idSucursal")
	@ManyToOne(optional = false)
	private Sucursal sucursalIdSucursal;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "recoleccionIdRecoleccion")
	private List<RecoleccionBillete> recoleccionBilleteList;

	public void calcularTotal() {
		if (recoleccionBilleteList != null) {
			this.totalRecoleccion = recoleccionBilleteList.stream().mapToDouble(RecoleccionBillete::getSubtotal).sum();
		} else {
			this.totalRecoleccion = 0;
		}
	}

	public Recoleccion(Sucursal sucursalIdSucursal, double totalRecoleccion) {
		super();
		this.createdAt = LocalDateTime.now();
		this.sucursalIdSucursal = sucursalIdSucursal;
		this.totalRecoleccion = totalRecoleccion;
	}

}
