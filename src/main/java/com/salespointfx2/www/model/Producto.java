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
public class Producto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(nullable = false)
	private Integer idProducto;

	@Column(length = 60, nullable = false)
	private String nombreProducto;

	@Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT true")
	private boolean estatusProducto;

	@Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT true")
	private boolean vendible;

	@Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT true")
	private boolean esPaquete;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	@Basic(optional = true)
	@Column(nullable = true)
	private LocalDateTime updatedAt;

	@Basic(optional = true)
	@Column(nullable = true)
	private LocalDateTime deletedAt;

	

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "productoIdProducto")
	private List<PaqueteProducto> paqueteProductoList;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "productoIdProducto")
	private List<SucursalProducto> sucursalProductoList;

	public Producto(String nombreProducto, boolean estatusProducto, boolean vendible, boolean esPaquete/*, Categoria categoriaIdCategoria*/) {
		super();
		this.nombreProducto = nombreProducto;
		this.estatusProducto = estatusProducto;
		this.vendible = vendible;
		//this.categoriaIdCategoria = categoriaIdCategoria;
		this.esPaquete = esPaquete;
		this.createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
	}

}