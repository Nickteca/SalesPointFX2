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
public class PaqueteProducto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(nullable = false)
	private Integer idPaqueteProducto;

	@JoinColumn(name = "paqueteIdPaquete", referencedColumnName = "idProducto")
	@ManyToOne(optional = false)
	private Producto paqueteIdPaquete;

	@Basic(optional = false)
	@Column(nullable = false)
	private float cantidad;

	@JoinColumn(name = "productoIdProducto", referencedColumnName = "idProducto")
	@ManyToOne(optional = false)
	private Producto productoIdProducto;

	public PaqueteProducto(Producto paqueteIdPaquete, Producto productoIdProducto, float cantidad) {
		super();
		this.paqueteIdPaquete = paqueteIdPaquete;
		this.productoIdProducto = productoIdProducto;
		this.cantidad = cantidad;
	}

}
