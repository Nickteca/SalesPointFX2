package com.salespointfx2.www.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salespointfx2.www.model.GastoSucursal;
import java.util.List;
import com.salespointfx2.www.model.Sucursal;


public interface GastoSucursalRepo extends JpaRepository<GastoSucursal, Short> {
	List<GastoSucursal> findBySucursalIdSucursal(Sucursal sucursalIdSucursal);
}
