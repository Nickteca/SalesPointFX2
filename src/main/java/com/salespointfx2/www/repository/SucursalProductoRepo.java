package com.salespointfx2.www.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salespointfx2.www.model.Categoria;
import com.salespointfx2.www.model.Sucursal;
import com.salespointfx2.www.model.SucursalProducto;

public interface SucursalProductoRepo extends JpaRepository<SucursalProducto, Integer> {
	List<SucursalProducto> findBySucursalIdSucursalAndVendibleTrue(Sucursal sucursal);

	List<SucursalProducto> findBySucursalIdSucursalAndVendibleTrueAndCategoriaIdCategoria(Sucursal sucursal, Categoria categoria);

}
