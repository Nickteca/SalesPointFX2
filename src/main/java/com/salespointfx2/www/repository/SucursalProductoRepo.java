package com.salespointfx2.www.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.salespointfx2.www.model.Categoria;
import com.salespointfx2.www.model.Sucursal;
import com.salespointfx2.www.model.SucursalProducto;

public interface SucursalProductoRepo extends JpaRepository<SucursalProducto, Integer> {
	List<SucursalProducto> findBySucursalIdSucursalAndVendibleTrue(Sucursal sucursal);

	List<SucursalProducto> findBySucursalIdSucursalAndVendibleTrueAndCategoriaIdCategoria(Sucursal sucursal, Categoria categoria);

	@Query("SELECT sp FROM SucursalProducto sp JOIN FETCH sp.productoIdProducto WHERE sp.idSucursalProducto = :id")
	Optional<SucursalProducto> findByIdWithProducto(@Param("id") int id);

	List<SucursalProducto> findBySucursalIdSucursalAndProductoIdProductoEsPaqueteFalse(Sucursal sucursal);
}
