package com.salespointfx2.www.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salespointfx2.www.model.Categoria;

public interface CategoriaRepo extends JpaRepository<Categoria, Short> {
	/*
	 * List<Categoria>
	 * findByProductoListVendibleTrueAndProductoListSucursalIdSucursal(Sucursal
	 * sucursal);
	 * 
	 * @Query("SELECT DISTINCT c FROM Categoria c " + "JOIN c.productoList sp " +
	 * "WHERE sp.vendible = true " +
	 * "AND sp.sucursalIdSucursal.idSucursal = :sucursalId") List<Categoria>
	 * findCategoriasConProductosVendiblesBySucursal(@Param("sucursalId") Short
	 * sucursalId);
	 */

}
