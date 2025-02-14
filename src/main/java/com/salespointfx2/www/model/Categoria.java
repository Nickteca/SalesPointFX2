package com.salespointfx2.www.model;

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
public class Categoria {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(nullable = false)
	private Short idCategoria;

	@Column(length = 20, nullable = false, unique = true)
	private String nombreCategoria;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "categoriaIdCategoria"/* , fetch = FetchType.EAGER */)
	private List<SucursalProducto> productoList;

	public Categoria(String nombreCategoria) {
		super();
		this.nombreCategoria = nombreCategoria;
	}

	public Categoria(Short idCategoria) {
		super();
		this.idCategoria = idCategoria;
	}
}