package com.salespointfx2.www.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salespointfx2.www.model.Sucursal;
import com.salespointfx2.www.model.Venta;

public interface VentaRepo extends JpaRepository<Venta, Integer> {
	List<Venta> findBySucursalIdSucursalAndCreatedAtBetween(Sucursal sucursal, LocalDateTime inicio, LocalDateTime fin);
}