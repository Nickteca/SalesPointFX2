package com.salespointfx2.www.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salespointfx2.www.model.MovimientoCaja;
import com.salespointfx2.www.model.Sucursal;

public interface MovimientoCajaRepo extends JpaRepository<MovimientoCaja, Integer> {
	Optional<MovimientoCaja> findFirstBySucursalIdSucursalOrderByIdMovimientoCajaDesc(Sucursal sucursal);
}
