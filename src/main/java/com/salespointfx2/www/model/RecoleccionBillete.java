package com.salespointfx2.www.model;

import java.io.Serializable;

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
public class RecoleccionBillete implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, unique = true)
	private int idRecoleccionBillete;

	@Column(nullable = false)
	private short billete;

	@Column(nullable = false)
	private short cantidad;

	@Column(nullable = false)
	private double subtotal;

	@JoinColumn(name = "recoleccionIdRecoleccion", referencedColumnName = "idRecoleccion")
	@ManyToOne(optional = false)
	private Recoleccion recoleccionIdRecoleccion;

	public RecoleccionBillete(short billete, short cantidad) {
		super();
		this.billete = billete;
		this.cantidad = cantidad;
		this.subtotal = billete * cantidad;
	}

}
